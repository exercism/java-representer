package representer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class RepresenterCli {
    private static final Logger logger = LoggerFactory.getLogger(RepresenterCli.class);

    public static void main(String[] args) throws IOException {
        OptionsValidator validator = new OptionsValidator();
        if (!validator.isValid(args)) {
            throw new IllegalArgumentException(
                    "expected 2 args: exercise-slug and exercise-context-path");
        }
        final String slug = args[0];
        final String contextPath = args[1];

        logger.info("Parameters slug: {}, contextPath: {}", slug, contextPath);
        if (!validator.isValidContext(contextPath)) {
            throw new IllegalArgumentException(
                    "exercise-context-path requires the ending trailing slash");
        }

        var solution = new SubmittedSolution(slug, Path.of(contextPath));
        var representation = Representer.generate(solution);

        try (var representationWriter = new FileWriter(contextPath + "representation.txt");
        var mappingWriter = new FileWriter(contextPath + "mapping.json");
        var metadataWriter = new FileWriter(contextPath + "representation.json")) {
            var outputWriter = new OutputWriter(representationWriter, mappingWriter, metadataWriter);
            outputWriter.write(representation);
        }
    }
}
