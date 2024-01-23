package representer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.printer.DefaultPrettyPrinterVisitor;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import representer.normalizer.PlaceholderNormalizer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public String generate(CompilationUnit unit) {
        voidNormalizers.forEach(n -> unit.accept(n, null));
        genericNormalizers.forEach(n -> unit.accept(n, null));
        DefaultPrettyPrinterVisitor visitor = new DefaultPrettyPrinterVisitor(new DefaultPrinterConfiguration());
        unit.accept(visitor, null);
        return visitor.toString();
    }

    public Optional<PlaceholderNormalizer> placeholderNormalizer() {
        return placeholderNormalizer(voidNormalizers);
    }

    private Optional<PlaceholderNormalizer> placeholderNormalizer(
            List<VoidVisitor<String>> normalizers) {
        return normalizers.stream().filter(n -> n.getClass() == PlaceholderNormalizer.class)
                .map(n -> (PlaceholderNormalizer) n)
                .findFirst();
    }

}
