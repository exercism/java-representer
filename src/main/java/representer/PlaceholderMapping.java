package representer;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderMapping implements PlaceholderGenerator {
    private static final String PLACEHOLDER_PREFIX = "PLACEHOLDER_";

    private final Map<String, String> placeholders = new HashMap<>();

    @Override
    public String getPlaceholder(String identifier) {
        if (identifier.startsWith(PLACEHOLDER_PREFIX)) {
            return identifier;
        }

        return this.placeholders.computeIfAbsent(identifier, x -> createPlaceholder());
    }

    private String createPlaceholder() {
        return PLACEHOLDER_PREFIX + (this.placeholders.size() + 1);
    }

    public Map<String, String> getPlaceholders() {
        return Map.copyOf(this.placeholders);
    }
}
