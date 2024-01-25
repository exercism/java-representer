package representer;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderMapping implements PlaceholderGenerator {
    private final Map<String, String> placeholders = new HashMap<>();

    @Override
    public String getPlaceholder(String identifier) {
        return this.placeholders.computeIfAbsent(identifier, x -> createPlaceholder());
    }

    private String createPlaceholder() {
        return "PLACEHOLDER_" + (this.placeholders.size() + 1);
    }

    public Map<String, String> getPlaceholders() {
        return Map.copyOf(this.placeholders);
    }
}
