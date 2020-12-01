import java.util.Arrays;
import java.util.List;

class PigLatinTranslator {
	private List<Character> vowels = Arrays.asList('a','e','i','o','u');
	
	
	
	String translate(String phrase) {
		if(phrase.chars().anyMatch(c -> vowels.contains((char) c))) {
			return phrase + "ay";
		}
		
		return "";
	}
}
