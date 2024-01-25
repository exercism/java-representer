package representer;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

class OutputWriter {
    private static final int REPRESENTER_VERSION = 1;
    private static final int JSON_INDENTATION = 2;

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
        var json = new JSONObject();
        mapping.forEach(json::put);
        this.mappingWriter.write(json.toString(JSON_INDENTATION));
    }

    private void writeMetadata() throws IOException {
        var json = new JSONObject()
                .put("version", REPRESENTER_VERSION);
        this.metadataWriter.write(json.toString(JSON_INDENTATION));
    }
}
