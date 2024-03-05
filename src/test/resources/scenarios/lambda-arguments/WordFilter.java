import java.util.List;
import java.util.stream.Collectors;

class WordFilter {
    List<String> filter(List<String> words) {
        return words
                .stream()
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());
    }
}
