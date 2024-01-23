package representer;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import representer.normalizer.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExercisesTest {
    
    private Representer representer;
    private final TestUtils testUtils = new TestUtils();
    
    @Before
    public void init() {
    	List<ModifierVisitor<String>> genericNormalizers = Arrays.asList(new PackageNormalizer(), new ImportNormalizer(), new CommentNormalizer(), new BlockNormalizer());
    	List<VoidVisitor<String>> voidNormalizers = Arrays.asList(new PlaceholderNormalizer());
    	representer = new Representer(genericNormalizers, voidNormalizers);
    }
    
    @Test
    public void accumulateRepresentation() throws Exception {
    	String testFolder = "exercises/accumulate";
    	List<CompilationUnit> sources = testUtils.getParsedResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void acronymRepresentation() throws Exception {
    	String testFolder = "exercises/acronym";
    	List<CompilationUnit> sources = testUtils.getParsedResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void affineCipherRepresentation() throws Exception {
    	String testFolder = "exercises/affine-cipher";
    	List<CompilationUnit> sources = testUtils.getParsedResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void allergiesRepresentation() throws Exception {
    	String testFolder = "exercises/allergies";
    	List<CompilationUnit> sources = testUtils.getParsedResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void allYourBaseRepresentation() throws Exception {
    	String testFolder = "exercises/all-your-base";
    	List<CompilationUnit> sources = testUtils.getParsedResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void alphameticsRepresentation() throws Exception {
    	String testFolder = "exercises/alphametics";
    	List<CompilationUnit> sources = testUtils.getParsedResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void helloWorldRepresentation() throws Exception {
    	String testFolder = "exercises/hello-world";
    	List<CompilationUnit> sources = testUtils.getParsedResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void pigLatinRepresentation() throws Exception {
    	String testFolder = "exercises/pig-latin";
    	List<CompilationUnit> sources = testUtils.getParsedResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void raindropsRepresentation() throws Exception {
    	String testFolder = "exercises/raindrops";
    	List<CompilationUnit> sources = testUtils.getParsedResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
}
