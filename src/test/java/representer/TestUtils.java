package representer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceRoot;

import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {
	public String getResourceContent(String name) throws Exception {
		URI resource = TestUtils.class.getClassLoader().getResource(name).toURI();
		return new String(Files.readAllBytes(Paths.get(resource)));
	}

	public CompilationUnit getParsedResourceContent(String name) throws Exception {
		var parser = new JavaParser(ParserConfigurationFactory.getParserConfiguration());
		return parser.parse(getResourceContent(name)).getResult().get();
	}
	
	public List<CompilationUnit> getParsedResourceContentFromFolder(String folder) throws Exception {
		URL resourceURL = TestUtils.class.getClassLoader().getResource(folder);
		URI resource = resourceURL.toURI();
		SourceRoot sourceRoot = new SourceRoot(Path.of(resource))
				.setParserConfiguration(ParserConfigurationFactory.getParserConfiguration());

		List<CompilationUnit> sources = new ArrayList<>();
		for (var result : sourceRoot.tryToParse()) {
			sources.add(result.getResult().get());
		}

		return sources;
	}
}
