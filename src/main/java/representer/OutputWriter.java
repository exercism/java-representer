package representer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

class OutputWriter {
    private static final int REPRESENTER_VERSION = 2;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Logger LOGGER = LoggerFactory.getLogger(OutputWriter.class);

    private final Path outputPath;

    OutputWriter(Path outputPath) {
        this.outputPath = outputPath;
    }

    void write(Representation representation) throws IOException {
        writeRepresentation(representation.representation(), this.outputPath.resolve("representation.txt"));
        writePlaceholders(representation.placeholders(), this.outputPath.resolve("mapping.json"));
        writeMetadata(this.outputPath.resolve("representation.json"));
    }

    private void writeRepresentation(String representation, Path path) throws IOException {
        LOGGER.info("Writing representation to {}", path);
        try (var writer = new FileWriter(path.toFile())) {
            writer.write(representation);
        }
    }

    private void writePlaceholders(Map<String, String> mapping, Path path) throws IOException {
        LOGGER.info("Writing placeholder mapping to {}", path);
        var sorted = new TreeMap<>(mapping);
        try (var writer = new FileWriter(path.toFile())) {
            writer.write(GSON.toJson(sorted));
        }
    }

    private void writeMetadata(Path path) throws IOException {
        LOGGER.info("Writing representation metadata to {}", path);
        var json = new JsonObject();
        json.addProperty("version", REPRESENTER_VERSION);
        try (var writer = new FileWriter(path.toFile())) {
            writer.write(GSON.toJson(json));
        }
    }
}
