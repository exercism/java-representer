package representer;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestUtils {
	public String getResourceContent(String name) throws Exception {
		URI resource = TestUtils.class.getClassLoader().getResource(name).toURI();
		return new String(Files.readAllBytes(Paths.get(resource)));
	}
	
	public List<String> getResourceContentFromFolder(String folder) throws Exception {
		URL resourceURL = TestUtils.class.getClassLoader().getResource(folder); 
		URI resource = resourceURL.toURI();
		String[] sources = new File(resource).list();
		Stream<String> javaSource = Stream.of(sources).filter(s -> s.endsWith(".java")).sorted();
		List<String> sourcesContents = new ArrayList<>();
		   javaSource.forEach(s -> {
	            try {
	                final String source = resourceURL.getFile() + "/" + s;
	                String sourceFileContent = new String(Files.readAllBytes(Paths.get(source)));
	                sourcesContents.add(sourceFileContent);
	            } catch (IOException e) {
	                throw new RuntimeException("Errors reading the sources inside the folder " + folder);
	            }
	        });
		return sourcesContents;
	}
}
