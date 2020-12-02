package representer;

import java.util.Arrays;
import java.util.List;

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
    	String sourceContent = testUtils.getResourceContent(testFolder + "/Accumulate.java");
    	String representation = representer.generate(sourceContent, fakeRepresentationSerializator, fakeMappingSerializator);
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void acronymRepresentation() throws Exception {
    	String testFolder = "exercises/acronym";
    	String sourceContent = testUtils.getResourceContent(testFolder + "/Acronym.java");
    	String representation = representer.generate(sourceContent, fakeRepresentationSerializator, fakeMappingSerializator);
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void affineCipherRepresentation() throws Exception {
    	String testFolder = "exercises/affine-cipher";
    	String sourceContent = testUtils.getResourceContent(testFolder + "/AffineCipher.java");
    	String representation = representer.generate(sourceContent, fakeRepresentationSerializator, fakeMappingSerializator);
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void allergiesRepresentation() throws Exception {
    	String testFolder = "exercises/allergies";
    	String sourceContent = testUtils.getResourceContent(testFolder + "/Allergies.java");
    	String representation = representer.generate(sourceContent, fakeRepresentationSerializator, fakeMappingSerializator);
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void allYourBaseRepresentation() throws Exception {
    	String testFolder = "exercises/all-your-base";
    	String sourceContent = testUtils.getResourceContent(testFolder + "/BaseConverter.java");
    	String representation = representer.generate(sourceContent, fakeRepresentationSerializator, fakeMappingSerializator);
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void alphameticsRepresentation() throws Exception {
    	String testFolder = "exercises/alphametics";
    	String sourceContent = testUtils.getResourceContent(testFolder + "/Alphametics.java");
    	String representation = representer.generate(sourceContent, fakeRepresentationSerializator, fakeMappingSerializator);
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void helloWorldRepresentation() throws Exception {
    	String testFolder = "exercises/hello-world";
    	String sourceContent = testUtils.getResourceContent(testFolder + "/Greeter.java");
    	String representation = representer.generate(sourceContent, fakeRepresentationSerializator, fakeMappingSerializator);
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void pigLatinRepresentation() throws Exception {
    	String testFolder = "exercises/pig-latin";
    	String sourceContent = testUtils.getResourceContent(testFolder + "/PigLatinTranslator.java");
    	String representation = representer.generate(sourceContent, fakeRepresentationSerializator, fakeMappingSerializator);
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
    
    @Test
    public void raindropsRepresentation() throws Exception {
    	String testFolder = "exercises/raindrops";
    	String sourceContent = testUtils.getResourceContent(testFolder + "/RaindropConverter.java");
    	String representation = representer.generate(sourceContent, fakeRepresentationSerializator, fakeMappingSerializator);
    	String expectedRepresentation = testUtils.getResourceContent(testFolder + "/representation");
    	System.out.println(representation);
    	Assertions.assertThat(representation).isEqualTo(expectedRepresentation);
    }
}
