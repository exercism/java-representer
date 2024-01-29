package representer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class RepresenterCli {
    private static final Logger logger = LoggerFactory.getLogger(RepresenterCli.class);

    private static Path validateDirectory(String directory) {
        var file = new File(directory);

        if (!file.exists() || !file.isDirectory()) {
            throw new IllegalArgumentException("Not a valid directory: " + directory);
        }

        return file.toPath();
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            throw new IllegalArgumentException("expected 3 args: exercise-slug; exercise-input-path; output-path");
        }

        var slug = args[0];
        var inputDirectory = validateDirectory(args[1]);
        var outputDirectory = validateDirectory(args[2]);

        logger.info("Parameters slug: {}, input directory: {}, output directory: {}", slug, inputDirectory, outputDirectory);

        var outputWriter = new OutputWriter(outputDirectory);
        var representation = Representer.generate(inputDirectory.resolve("src/main/java").toString());
        outputWriter.write(representation);
    }
}
