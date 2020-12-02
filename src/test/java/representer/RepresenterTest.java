package representer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Test;

import representer.normalizer.BlockNormalizer;
import representer.normalizer.CommentNormalizer;
import representer.normalizer.ImportNormalizer;
import representer.normalizer.PackageNormalizer;
import representer.normalizer.PlaceholderNormalizer;

public class RepresenterTest {

    private FakeRepresentationSerializator fakeRepresentationSerializator = new FakeRepresentationSerializator();
    private FakeMappingSerializator fakeMappingSerializator = new FakeMappingSerializator();

    private TestUtils testUtils = new TestUtils();
    @Test
    public void simple_scenario() throws Exception {
        String sourceCode = testUtils.getResourceContent("representer/simple_scenario_input");
        Representer representer = new Representer(null, Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized =
                representer.generate(sourceCode, fakeRepresentationSerializator,
                        fakeMappingSerializator);
        final String expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(codeNormalized, is(expected));
    }

    @Test
    public void package_scenario() throws Exception {
        String sourceCode = testUtils.getResourceContent("representer/package_scenario_input");
        Representer representer = new Representer(Arrays.asList(new PackageNormalizer()),
                Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized =
                representer.generate(sourceCode, fakeRepresentationSerializator,
                        fakeMappingSerializator);
        final String expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(codeNormalized, is(expected));
    }

    @Test
    public void comments_scenario() throws Exception {
        String sourceCode = testUtils.getResourceContent("representer/comments_scenario_input");
        Representer representer =
                new Representer(Arrays.asList(new PackageNormalizer(), new CommentNormalizer()),
                        Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized =
                representer.generate(sourceCode, fakeRepresentationSerializator,
                        fakeMappingSerializator);
        final String expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(codeNormalized, is(expected));
    }


    @Test
    public void import_scenario() throws Exception {
        String sourceCode = testUtils.getResourceContent("representer/import_scenario_input");
        Representer representer =
                new Representer(
                        Arrays.asList(new PackageNormalizer(), new CommentNormalizer(),
                                new ImportNormalizer()),
                        Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized =
                representer.generate(sourceCode, fakeRepresentationSerializator,
                        fakeMappingSerializator);
        final String expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(codeNormalized, is(expected));
    }

    @Test
    public void block_scenario() throws Exception {
        String sourceCode = testUtils.getResourceContent("representer/block_scenario_input");
        Representer representer =
                new Representer(Arrays.asList(new PackageNormalizer(), new BlockNormalizer()),
                        Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized =
                representer.generate(sourceCode, fakeRepresentationSerializator,
                        fakeMappingSerializator);
        final String expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(codeNormalized, is(expected));
    }

  
}
