package representer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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

    @Test
    public void simple_scenario() {
        String twoFer = "  class      Twofer {" + "    String    twofer(String     name) {"
                + "        return   \"One for \" + "
                + "            (name != null ? name : \"you\")  +    \",   one for me.\";"
                + "     }" + " }";
        Representer representer = new Representer(null, Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized =
                representer.generate(twoFer, fakeRepresentationSerializator, fakeMappingSerializator);
        final String expected = "class PLACEHOLDER_1 {\n\n"
                + "    String PLACEHOLDER_2(String PLACEHOLDER_3) {\n"
                + "        return \"One for \" + (PLACEHOLDER_3 != null ? PLACEHOLDER_3 : \"you\") + \",   one for me.\";\n"
                + "    }\n}\n";
        assertThat(codeNormalized, is(expected));
    }

    @Test
    public void package_scenario() {
        String twoFer = "package myTest;\n\n\n\n  class      Twofer {"
                + "    String    twofer(String     name) {" + "        return   \"One for \" + "
                + "            (name != null ? name : \"you\")  +    \",   one for me.\";"
                + "     }" + " }";
        Representer representer = new Representer(Arrays.asList(new PackageNormalizer()),
                Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized =
                representer.generate(twoFer, fakeRepresentationSerializator, fakeMappingSerializator);
        final String expected =
                "class PLACEHOLDER_1 {\n\n" + "    String PLACEHOLDER_2(String PLACEHOLDER_3) {\n"
                        + "        return \"One for \" + (PLACEHOLDER_3 != null ? PLACEHOLDER_3 : \"you\") + \",   one for me.\";\n"
                        + "    }\n}\n";
        assertThat(codeNormalized, is(expected));
    }

    @Test
    public void comments_scenario() {
        String twoFer =
                "/* multiline \n \n comment \n\n */   package myTest;\n\n\n\n  class      Twofer {"
                + "// mycomment \n\n    String    twofer(String     name) {"
                + "        return   \"One for \" + "
                + "            (name != null ? name : \"you\")  +    \",   one for me.\";"
                + "     }" + " }";
        Representer representer =
                new Representer(Arrays.asList(new PackageNormalizer(), new CommentNormalizer()),
                        Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized =
                representer.generate(twoFer, fakeRepresentationSerializator, fakeMappingSerializator);
        final String expected =
                "class PLACEHOLDER_1 {\n\n" + "    String PLACEHOLDER_2(String PLACEHOLDER_3) {\n"
                        + "        return \"One for \" + (PLACEHOLDER_3 != null ? PLACEHOLDER_3 : \"you\") + \",   one for me.\";\n"
                        + "    }\n}\n";
        assertThat(codeNormalized, is(expected));
    }


    @Test
    public void import_scenario() {
        String twoFer =
                "/* multiline \n \n comment \n\n */   package myTest;\n\n\n\n  import java.util.Random;import java.lang.Integer;\n\n\n class      Twofer {"
                        + "// mycomment \n\n    String    twofer(String     name) {"
                        + "        return   \"One for \" + "
                        + "            (name != null ? name : \"you\")  +    \",   one for me.\";"
                        + "     }" + " }";
        Representer representer =
                new Representer(
                        Arrays.asList(new PackageNormalizer(), new CommentNormalizer(),
                                new ImportNormalizer()),
                        Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized =
                representer.generate(twoFer, fakeRepresentationSerializator, fakeMappingSerializator);
        final String expected =
                "class PLACEHOLDER_1 {\n\n" + "    String PLACEHOLDER_2(String PLACEHOLDER_3) {\n"
                        + "        return \"One for \" + (PLACEHOLDER_3 != null ? PLACEHOLDER_3 : \"you\") + \",   one for me.\";\n"
                        + "    }\n}\n";
        assertThat(codeNormalized, is(expected));
    }

    @Test
    public void block_scenario() {
        String twoFer = "package myTest;\n\n\n\n  class      Twofer {"
                + "    String    twofer(String     name) {" + "        return   \"One for \" + "
                + "            (name != null ? name : \"you\")  +    \",   one for me.\";"
                + "     }" + " }";
        Representer representer =
                new Representer(Arrays.asList(new PackageNormalizer(), new BlockNormalizer()),
                        Arrays.asList(new PlaceholderNormalizer()));
        final String codeNormalized =
                representer.generate(twoFer, fakeRepresentationSerializator, fakeMappingSerializator);
        final String expected =
                "class PLACEHOLDER_1 {\n\n" + "    String PLACEHOLDER_2(String PLACEHOLDER_3) {\n"
                        + "        return \"One for \" + (PLACEHOLDER_3 != null ? PLACEHOLDER_3 : \"you\") + \",   one for me.\";\n"
                        + "    }\n}\n";
        assertThat(codeNormalized, is(expected));
    }

}
