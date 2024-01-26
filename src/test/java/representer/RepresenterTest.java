package representer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RepresenterTest {
    @ParameterizedTest
    @MethodSource("scenarios")
    public void testRepresentation(String scenario) throws IOException {
        var expected = expected(scenario);
        var actual = Representer.generate(path(scenario));
        assertThat(actual.representation()).isEqualTo(expected);
    }

    private static Stream<String> scenarios() {
        return Stream.of(
                "simple",
                "class-and-enum",
                "class-with-nested-enum",
                "lambda-arguments",
                "generic-type-arguments",
                "if-statements-without-block-bodies"
        );
    }

    private String path(String scenario) {
        return getClass().getResource("/scenarios/" + scenario).getPath();
    }

    private String expected(String scenario) throws IOException {
        try (var stream = getClass().getResourceAsStream("/scenarios/" + scenario + "/representation.txt")) {
            return new String(stream.readAllBytes());
        }
    }
}
