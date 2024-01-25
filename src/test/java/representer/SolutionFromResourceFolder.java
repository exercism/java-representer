package representer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SolutionFromResourceFolder implements Solution {
    private final String slug;
    private final List<CompilationUnit> compilationUnits;

    public SolutionFromResourceFolder(String slug, String resourceFolder) throws Exception {
        this.slug = slug;
        this.compilationUnits = new ArrayList<>();

        var resourceUrl = getClass().getResource("/" + resourceFolder);
        var sourceRoot = new SourceRoot(Path.of(resourceUrl.toURI()))
                .setParserConfiguration(ParserConfigurationFactory.getParserConfiguration());

        for (var result : sourceRoot.tryToParse()) {
            this.compilationUnits.add(result.getResult().get());
        }
    }

    @Override
    public String getSlug() {
        return this.slug;
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        return List.copyOf(this.compilationUnits);
    }
}