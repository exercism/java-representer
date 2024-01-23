package representer;

import com.github.javaparser.ast.CompilationUnit;
import org.junit.Test;
import representer.normalizer.*;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RepresenterTest {
    private final TestUtils testUtils = new TestUtils();

    @Test
    public void simple_scenario() throws Exception {
        CompilationUnit sourceCode = testUtils.getParsedResourceContent("representer/simple_scenario_input");
        Representer representer = new Representer(null, Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized = representer.generate(sourceCode);
        final String expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(codeNormalized, is(expected));
    }

    @Test
    public void package_scenario() throws Exception {
        CompilationUnit sourceCode = testUtils.getParsedResourceContent("representer/package_scenario_input");
        Representer representer = new Representer(Arrays.asList(new PackageNormalizer()),
                Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized = representer.generate(sourceCode);
        final String expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(codeNormalized, is(expected));
    }

    @Test
    public void comments_scenario() throws Exception {
        CompilationUnit sourceCode = testUtils.getParsedResourceContent("representer/comments_scenario_input");
        Representer representer =
                new Representer(Arrays.asList(new PackageNormalizer(), new CommentNormalizer()),
                        Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized = representer.generate(sourceCode);
        final String expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(codeNormalized, is(expected));
    }


    @Test
    public void import_scenario() throws Exception {
        CompilationUnit sourceCode = testUtils.getParsedResourceContent("representer/import_scenario_input");
        Representer representer =
                new Representer(
                        Arrays.asList(new PackageNormalizer(), new CommentNormalizer(),
                                new ImportNormalizer()),
                        Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized = representer.generate(sourceCode);
        final String expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(codeNormalized, is(expected));
    }

    @Test
    public void block_scenario() throws Exception {
        CompilationUnit sourceCode = testUtils.getParsedResourceContent("representer/block_scenario_input");
        Representer representer =
                new Representer(Arrays.asList(new PackageNormalizer(), new BlockNormalizer()),
                        Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized = representer.generate(sourceCode);
        final String expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(codeNormalized, is(expected));
    }

  
}
