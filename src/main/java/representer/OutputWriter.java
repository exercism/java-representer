package representer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;

class OutputWriter {
    private static final int REPRESENTER_VERSION = 2;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final Writer representationWriter;
    private final Writer mappingWriter;
    private final Writer metadataWriter;

    OutputWriter(Writer representationWriter, Writer mappingWriter, Writer metadataWriter) {
        this.representationWriter = representationWriter;
        this.mappingWriter = mappingWriter;
        this.metadataWriter = metadataWriter;
    }

    void write(Representation representation) throws IOException {
        writeRepresentation(representation.representation());
        writeMapping(representation.placeholders());
        writeMetadata();
    }

    private void writeRepresentation(String representation) throws IOException {
        this.representationWriter.write(representation);
    }

    private void writeMapping(Map<String, String> mapping) throws IOException {
        var sorted = new TreeMap<>(mapping);
        this.mappingWriter.write(GSON.toJson(sorted));
    }

    private void writeMetadata() throws IOException {
        var json = new JsonObject();
        json.addProperty("version", REPRESENTER_VERSION);
        this.metadataWriter.write(GSON.toJson(json));
    }
}
