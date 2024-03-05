package representer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OutputWriterTest {
    private static final String REPRESENTATION = """
            class PLACEHOLDER_1 {
                String PLACEHOLDER_3(String PLACEHOLDER_2) {
                    return "One for " + PLACEHOLDER_2 == null ? "you" : PLACEHOLDER_2 + ", one for me.";
                }
            }
            """;

    private static final Map<String, String> PLACEHOLDERS = Map.of(
            "PLACEHOLDER_1", "Twofer",
            "PLACEHOLDER_3", "twofer",
            "PLACEHOLDER_2", "name"
    );

    @TempDir
    static Path outputPath;

    @BeforeAll
    static void setup() throws IOException {
        var writer = new OutputWriter(outputPath);
        writer.write(new Representation(REPRESENTATION, PLACEHOLDERS));
    }

    @Test
    @DisplayName("Writes the representation to representation.txt")
    void writesRepresentationTxt() throws IOException {
        var actual = Files.readString(outputPath.resolve("representation.txt"));
        assertThat(actual).isEqualTo(REPRESENTATION);
    }

    @Test
    @DisplayName("Writes the placeholder mapping to mapping.json")
    void writesMappingJson() throws IOException {
        var actual = Files.readString(outputPath.resolve("mapping.json"));
        assertThat(actual).isEqualTo("""
                {
                  "PLACEHOLDER_1": "Twofer",
                  "PLACEHOLDER_2": "name",
                  "PLACEHOLDER_3": "twofer"
                }""");
    }

    @Test
    @DisplayName("Writes the representation metadata to representation.json")
    void writesRepresentationJson() throws IOException {
        var actual = Files.readString(outputPath.resolve("representation.json"));
        assertThat(actual).isEqualTo("""
                {
                  "version": 2
                }""");
    }
}
