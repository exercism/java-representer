package representer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlaceholderMappingTest {

    private PlaceholderMapping placeholderMapping;

    @BeforeEach
    public void setup() {
        placeholderMapping = new PlaceholderMapping();
    }

    @Test
    @DisplayName("Placeholders for two different identifiers are different")
    public void testDifferentIdentifiers() {
        assertThat(placeholderMapping.getPlaceholder("Twofer"))
                .isNotEqualTo(placeholderMapping.getPlaceholder("Hamming"));
    }

    @Test
    @DisplayName("The same identifier generates the same placeholder")
    public void testSameIdentifier() {
        var identifier = "Twofer";
        assertThat(placeholderMapping.getPlaceholder(identifier))
                .isEqualTo(placeholderMapping.getPlaceholder(identifier));
    }

    @Test
    @DisplayName("Identifiers are case-sensitive")
    public void testCaseSensitive() {
        assertThat(placeholderMapping.getPlaceholder("Twofer"))
                .isNotEqualTo(placeholderMapping.getPlaceholder("twofer"));
    }

    @Test
    @DisplayName("Generating placeholders for a placeholder does not create a new placeholder")
    public void testPlaceholderRecursion() {
        var placeholder = placeholderMapping.getPlaceholder("Twofer");
        assertThat(placeholderMapping.getPlaceholder(placeholder)).isEqualTo(placeholder);
    }
}
