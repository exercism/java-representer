package representer;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolutionFromResourceFiles implements Solution {
    private final String slug;
    private final List<CompilationUnit> compilationUnits;

    public SolutionFromResourceFiles(String slug, String resourceFileName, String... moreResourceFileNames) throws IOException {
        this.slug = slug;
        this.compilationUnits = new ArrayList<>();

        StaticJavaParser.setConfiguration(ParserConfigurationFactory.getParserConfiguration());
        compilationUnits.add(StaticJavaParser.parseResource(resourceFileName));
        for (String fileName : moreResourceFileNames) {
            compilationUnits.add(StaticJavaParser.parseResource(fileName));
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