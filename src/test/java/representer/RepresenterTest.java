package representer;

import org.approvaltests.Approvals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class RepresenterTest {
    @ParameterizedTest
    @MethodSource("scenarios")
    void testRepresentation(String scenario) {
        var actual = Representer.generate(path(scenario));
        Approvals.verify(actual.representation(), Approvals.NAMES.withParameters(scenario));
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
}
