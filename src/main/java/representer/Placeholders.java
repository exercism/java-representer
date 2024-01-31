package representer;

import java.util.HashMap;
import java.util.Map;

public class Placeholders implements PlaceholderGenerator {
    private static final String PLACEHOLDER_PREFIX = "PLACEHOLDER";

    private final Map<String, String> placeholders = new HashMap<>();

    @Override
    public boolean isPlaceholder(String identifier) {
        return identifier.startsWith(PLACEHOLDER_PREFIX);
    }

    @Override
    public String getPlaceholder(String identifier) {
        var placeholder = String.format("%s_%02d", PLACEHOLDER_PREFIX, this.placeholders.size() + 1);
        this.placeholders.put(placeholder, identifier);
        return placeholder;
    }

    public Map<String, String> getPlaceholders() {
        return Map.copyOf(this.placeholders);
    }
}
