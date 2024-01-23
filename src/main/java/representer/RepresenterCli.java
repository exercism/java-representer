package representer;

import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.utils.SourceRoot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import representer.normalizer.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RepresenterCli {

    private static final Logger logger = LogManager.getLogger(RepresenterCli.class);

    private static final List<ModifierVisitor<String>> modifierNormalizers =
            Arrays.asList(new PackageNormalizer(), new BlockNormalizer(), 
                    new CommentNormalizer(), new ImportNormalizer());

    private static final List<VoidVisitor<String>> voidNormalizers =
            Arrays.asList(new PlaceholderNormalizer());

    private static final String JAVA_PROJECT_STRUCTURE = "src/main/java";

    public static void main(String[] args) throws IOException {
        OptionsValidator validator = new OptionsValidator();
        if (!validator.isValid(args)) {
            throw new IllegalArgumentException(
                    "expected 2 args: exercise-slug and exercise-context-path");
        }
        final String slug = args[0];
        final String contextPath = args[1];

        logger.info("Parameters slug: {}, contextPath: {}", slug, contextPath);
        if (!validator.isValidContext(contextPath)) {
            throw new IllegalArgumentException(
                    "exercise-context-path requires the ending trailing slash");
        }

        var representer = new Representer(modifierNormalizers, voidNormalizers);
        var sourceRoot = new SourceRoot(Path.of(contextPath, JAVA_PROJECT_STRUCTURE))
                .setParserConfiguration(ParserConfigurationFactory.getParserConfiguration());
        var representations = new ArrayList<String>();
        
        try {
            for (var parseResult : sourceRoot.tryToParse()) {
                var compilationUnit = parseResult.getResult().get();
                var representation = representer.generate(compilationUnit);
                representations.add(representation);
            }
        } catch (IOException e) {
            logger.error("Problems reading the source files", e);
        }

        writeRepresentations(representations, contextPath);
        writeMetadata(contextPath);
        logger.info("Generated representation");

        if (representer.placeholderNormalizer().isPresent()) {
            var mapping = representer.placeholderNormalizer().get().mapping();
            writeMapping(mapping, contextPath);
            logger.info("Generated mapping");
        } else {
            logger.warn("PlaceholderNormalizer not loaded, mapping file will not be created");
        }
    }

    private static void writeRepresentations(List<String> representations, String outputDirectory) throws IOException {
        writeFile(String.join("\n", representations), outputDirectory + "representation.txt");
    }

    private static void writeMapping(Map<String, String> mapping, String outputDirectory) throws IOException {
        var json = new JSONObject();
        mapping.forEach(json::put);
        writeFile(json.toString(2), outputDirectory + "mapping.json");
    }

    private static void writeMetadata(String outputDirectory) throws IOException {
        var json = new JSONObject()
                .put("version", 1);
        writeFile(json.toString(2), outputDirectory + "representation.json");
    }

    private static void writeFile(String contents, String filePath) throws IOException {
        try (var writer = new FileWriter(filePath)) {
            writer.write(contents);
        }
    }
}
