package representer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;

import representer.normalizer.BlockNormalizer;
import representer.normalizer.CommentNormalizer;
import representer.normalizer.ImportNormalizer;
import representer.normalizer.PackageNormalizer;
import representer.normalizer.PlaceholderNormalizer;

public class RepresenterCli {

    private static final Logger logger = LogManager.getLogger(RepresenterCli.class);

    private static final List<ModifierVisitor<String>> modifierNormalizers =
            Arrays.asList(new PackageNormalizer(), new BlockNormalizer(), 
                    new CommentNormalizer(), new ImportNormalizer());

    private static final List<VoidVisitor<String>> voidNormalizers =
            Arrays.asList(new PlaceholderNormalizer());

    private static final String JAVA_PROJECT_STRUCTURE = "src/main/java";

    public static void main(String[] args) {
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


        Representer representer = new Representer(modifierNormalizers, voidNormalizers);
        final String sourceFolder = contextPath + slug + "/" + JAVA_PROJECT_STRUCTURE;
        logger.info("Search for source file into folder {}", sourceFolder);
        String[] sources = new File(sourceFolder).list();
        if (sources == null) {
            logger.error("Problems reading the folder");
            System.exit(-1);
        }
        Stream<String> javaSource = Stream.of(sources).filter(s -> s.endsWith(".java")).sorted();
        
        final RepresentationSerializatorImpl representationSerializator =
                new RepresentationSerializatorImpl(contextPath);
        final MappingSerializatorImpl mappingSerializator =
                new MappingSerializatorImpl(contextPath);

        javaSource.forEach(s -> {
            try {
                final String source = sourceFolder + "/" + s;
                String sourceFileContent = new String(Files.readAllBytes(Paths.get(source)));
                logger.info("Found source file {}", source);
                representer.generate(sourceFileContent, representationSerializator,
                        mappingSerializator);
                logger.info("Generated representation");
            } catch (IOException e) {
                logger.error("Problems reading the source file", e);
            }
        });
        Optional<PlaceholderNormalizer> placeholderNormalizer =
                representer.placeholderNormalizer();
        if (placeholderNormalizer.isPresent()) {
            mappingSerializator.serialize(placeholderNormalizer.get().mapping());
            logger.info("Generated mapping");
        } else {
            logger.warn("PlacelholderNormalizer not loaded, mapping file will not be created");
        }
    }



}
