package representer;

import java.util.Map;

public record Representation(String representation, Map<String, String> placeholders) {
    public Representation {
        placeholders = Map.copyOf(placeholders);
    }
}
