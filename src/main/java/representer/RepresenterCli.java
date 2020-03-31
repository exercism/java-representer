package representer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;

import representer.normalizer.BlockNormalizer;
import representer.normalizer.CommentNormalizer;
import representer.normalizer.ImportNormalizer;
import representer.normalizer.PackageNormalizer;
import representer.normalizer.PlaceholderNormalizer;

public class RepresenterCli {


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

        if (!validator.isValidContext(contextPath)) {
            throw new IllegalArgumentException(
                    "exercise-context-path requires the ending trailing slash");
        }


        Representer representer = new Representer(modifierNormalizers, voidNormalizers);
        final String sourceFolder = contextPath + slug + "/" + JAVA_PROJECT_STRUCTURE;
        String[] sources = new File(sourceFolder).list();
        Optional<String> javaSource =
                Stream.of(sources).filter(s -> s.endsWith(".java")).findFirst();
        
        javaSource.ifPresent(s -> {
            try {

                // String twoFer = "package myTest;\n\n\n\n class Twofer {"
                // + " String twofer(String name) {"
                // + " return \"One for \" + "
                // + " (name != null ? name : \"you\") + \", one for me.\";"
                // + " }" + " }";
                String twoFer = new String(Files.readAllBytes(Paths.get(sourceFolder, "/", s)));
                representer.generate(twoFer, new RepresentationSerializatorImpl(contextPath),
                        new MappingSerializatorImpl(contextPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }



}
