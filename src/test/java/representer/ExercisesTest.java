package representer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;

import representer.normalizer.BlockNormalizer;
import representer.normalizer.CommentNormalizer;
import representer.normalizer.ImportNormalizer;
import representer.normalizer.PackageNormalizer;
import representer.normalizer.PlaceholderNormalizer;

public class ExercisesTest {

	private FakeRepresentationSerializator fakeRepresentationSerializator = new FakeRepresentationSerializator();
    private FakeMappingSerializator fakeMappingSerializator = new FakeMappingSerializator();
    
    private Representer representer;
    private TestUtils testUtils = new TestUtils();
    
    @Before
    public void init() {
    	List<ModifierVisitor<String>> genericNormalizers = Arrays.asList(new PackageNormalizer(), new ImportNormalizer(), new CommentNormalizer(), new BlockNormalizer());
    	List<VoidVisitor<String>> voidNormalizers = Arrays.asList(new PlaceholderNormalizer());
    	representer = new Representer(genericNormalizers, voidNormalizers);
    }
    
    @Test
    public void accumulateRepresentation() throws Exception {
    	String testFolder = "exercises/accumulate";
    	List<String> sources = testUtils.getResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s, fakeRepresentationSerializator, fakeMappingSerializator)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void acronymRepresentation() throws Exception {
    	String testFolder = "exercises/acronym";
    	List<String> sources = testUtils.getResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s, fakeRepresentationSerializator, fakeMappingSerializator)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void affineCipherRepresentation() throws Exception {
    	String testFolder = "exercises/affine-cipher";
    	List<String> sources = testUtils.getResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s, fakeRepresentationSerializator, fakeMappingSerializator)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void allergiesRepresentation() throws Exception {
    	String testFolder = "exercises/allergies";
    	List<String> sources = testUtils.getResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s, fakeRepresentationSerializator, fakeMappingSerializator)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void allYourBaseRepresentation() throws Exception {
    	String testFolder = "exercises/all-your-base";
    	List<String> sources = testUtils.getResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s, fakeRepresentationSerializator, fakeMappingSerializator)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void alphameticsRepresentation() throws Exception {
    	String testFolder = "exercises/alphametics";
    	List<String> sources = testUtils.getResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s, fakeRepresentationSerializator, fakeMappingSerializator)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void helloWorldRepresentation() throws Exception {
    	String testFolder = "exercises/hello-world";
    	List<String> sources = testUtils.getResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s, fakeRepresentationSerializator, fakeMappingSerializator)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void pigLatinRepresentation() throws Exception {
    	String testFolder = "exercises/pig-latin";
    	List<String> sources = testUtils.getResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s, fakeRepresentationSerializator, fakeMappingSerializator)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void raindropsRepresentation() throws Exception {
    	String testFolder = "exercises/raindrops";
    	List<String> sources = testUtils.getResourceContentFromFolder(testFolder);
    	String representation = sources.stream().map(s -> representer.generate(s, fakeRepresentationSerializator, fakeMappingSerializator)).collect(Collectors.joining());
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
}
