package representer;

public interface PlaceholderGenerator {
    boolean isPlaceholder(String identifier);
    String getPlaceholder(String identifier);
}
