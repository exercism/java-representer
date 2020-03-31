package representer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;

import representer.normalizer.PlaceholderNormalizer;

public class Representer {
    private List<ModifierVisitor<String>> genericNormalizers;
    private List<VoidVisitor<String>> voidNormalizers;

    public Representer(List<ModifierVisitor<String>> genericNormalizers,
            List<VoidVisitor<String>> voidNormalizer) {
        this.voidNormalizers = voidNormalizer != null ? voidNormalizer : Collections.emptyList();
        this.genericNormalizers =
                genericNormalizers != null ? genericNormalizers : Collections.emptyList();
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
        } else {
            // placeholderNormalizer not present
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
