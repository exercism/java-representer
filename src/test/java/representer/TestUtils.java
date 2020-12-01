package representer;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestUtils {
	public String getResourceContent(String name) throws Exception {
		URI resource = TestUtils.class.getClassLoader().getResource(name).toURI();
		return new String(Files.readAllBytes(Paths.get(resource)));
	}
}
