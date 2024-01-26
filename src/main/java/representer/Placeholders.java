package representer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Placeholders implements PlaceholderGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(Placeholders.class);
    private static final String PLACEHOLDER_PREFIX = "PLACEHOLDER";

    private final Map<String, String> placeholders = new HashMap<>();

    @Override
    public String getPlaceholder(String identifier) {
        var placeholder = String.format("%s_%02d", PLACEHOLDER_PREFIX, this.placeholders.size() + 1);
        LOGGER.debug("Generated placeholder '{}' for identifier '{}'", placeholder, identifier);
        this.placeholders.put(placeholder, identifier);
        return placeholder;
    }

    public Map<String, String> getPlaceholders() {
        return Map.copyOf(this.placeholders);
    }
}
