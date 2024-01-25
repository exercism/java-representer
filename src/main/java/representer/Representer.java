package representer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.printer.DefaultPrettyPrinterVisitor;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;
import representer.normalizer.*;

class Representer {

    public static Representation generate(Solution solution) {
        var placeholders = new PlaceholderMapping();
        var normalization = new StringBuilder();

        for (CompilationUnit unit : solution.getCompilationUnits()) {
            unit.accept(new PlaceholderNormalizer(), placeholders);
            unit.accept(new PackageNormalizer(), null);
            unit.accept(new BlockNormalizer(), null);
            unit.accept(new CommentNormalizer(), null);
            unit.accept(new ImportNormalizer(), null);

            var printer = new DefaultPrettyPrinterVisitor(new DefaultPrinterConfiguration());
            unit.accept(printer, null);
            normalization.append(printer);
        }

        return new Representation(normalization.toString(), placeholders.getPlaceholders());
    }
}
