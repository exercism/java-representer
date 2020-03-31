package representer.normalizer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Mapper {
    private Map<String, String> mapping = new HashMap<>();
    private int counter = 0;

    public String getPlaceholder(String fieldName) {
        String placeholder = mapping.get(fieldName);
        if (placeholder == null) {
            placeholder = generate();
            mapping.put(fieldName, placeholder);
        }
        return placeholder;
    }

    private String generate() {
        return "PLACEHOLDER_" + (++counter);
    }

    public Map<String, String> getMapping() {
        return Collections.unmodifiableMap(mapping);
    }
}
