package representer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ExercisesTest {

    private final TestUtils testUtils = new TestUtils();

    @ParameterizedTest
    @MethodSource
    public void testExerciseRepresentations(String resourceFolder) throws Exception {
        var solution = new SolutionFromResourceFolder(resourceFolder.split("/")[1], resourceFolder);
        var expected = testUtils.getResourceContent(resourceFolder + "/representation");
        var actual = Representer.generate(solution);
        assertThat(String.join("\n", actual.representation())).isEqualTo(expected);
    }

    private static Stream<String> testExerciseRepresentations() {
        return Stream.of(
                "exercises/accumulate",
                "exercises/acronym",
                "exercises/affine-cipher",
                "exercises/all-your-base",
                "exercises/allergies",
                "exercises/alphametics",
                "exercises/hello-world",
                "exercises/pig-latin",
                "exercises/raindrops"
        );
    }
}
