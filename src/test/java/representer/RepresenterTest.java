package representer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RepresenterTest {
    private final TestUtils testUtils = new TestUtils();

    @ParameterizedTest
    @MethodSource
    public void testScenarios(String resourceFileName) throws Exception {
        var solution = new SolutionFromResourceFiles("two-fer", resourceFileName);
        var actual = Representer.generate(solution);
        var expected = testUtils.getResourceContent("representer/result_expected");
        assertThat(actual.representation()).isEqualTo(expected);
    }

    private static Stream<String> testScenarios() {
        return Stream.of(
                "representer/simple_scenario_input",
                "representer/package_scenario_input",
                "representer/comments_scenario_input",
                "representer/import_scenario_input",
                "representer/block_scenario_input"
        );
    }
}
