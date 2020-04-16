package representer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;

import representer.normalizer.PlaceholderNormalizer;

public class Representer {

    private static final Logger logger = LogManager.getLogger(Representer.class);

    private List<ModifierVisitor<String>> genericNormalizers;
    private List<VoidVisitor<String>> voidNormalizers;

    public Representer(List<ModifierVisitor<String>> genericNormalizers,
            List<VoidVisitor<String>> voidNormalizer) {
        this.voidNormalizers = voidNormalizer != null ? voidNormalizer : Collections.emptyList();
        this.genericNormalizers =
                genericNormalizers != null ? genericNormalizers : Collections.emptyList();
        if (logger.isInfoEnabled()) {
            List<String> loadedNormalizersNames = Stream
                    .concat(this.voidNormalizers.stream().map(n -> n.getClass().getSimpleName()),
                            this.genericNormalizers.stream().map(n -> n.getClass().getSimpleName()))
                    .collect(Collectors.toList());
            logger.info("Normalizers loaded: {}", loadedNormalizersNames);
        }
    }

    public String generate(String sourceContent,
            RepresentationSerializator representationSerializator,
            MappingSerializator mappingSerializator) {
        CompilationUnit unit = StaticJavaParser.parse(sourceContent);
        voidNormalizers.forEach(n -> unit.accept(n, null));
        genericNormalizers.forEach(n -> unit.accept(n, null));
        String representation = unit.toString();
        representationSerializator.serialize(representation);
        Optional<PlaceholderNormalizer> placeholderNormalizer =
                placeholderNormalizer(voidNormalizers);
        if (placeholderNormalizer.isPresent()) {
            mappingSerializator.serialize(placeholderNormalizer.get().mapping());
            logger.info("Generated mapping file");
        } else {
            logger.warn("PlacelholderNormalizer not loaded, mapping file will not be created");
        }
        return representation;
    }

    private Optional<PlaceholderNormalizer> placeholderNormalizer(
            List<VoidVisitor<String>> normalizers) {
        return normalizers.stream().filter(n -> n.getClass() == PlaceholderNormalizer.class)
                .map(n -> (PlaceholderNormalizer) n)
                .findFirst();
    }

}
