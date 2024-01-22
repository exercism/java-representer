package representer;

import java.io.FileWriter;
import java.io.IOException;

public class RepresentationSerializatorImpl implements RepresentationSerializator {
    private static final String REPRESENTATION_FILE = "representation.txt";
    private static final String REPRESENTATION_METADATA_FILE = "representation.json";
    private static final String REPRESENTATION_METADATA = "{\"version\": 1}";
    private final FileWriter fileWriter;
    private final FileWriter metadataFileWriter;

    public RepresentationSerializatorImpl(String folderPath) {
        String representationFilePath = folderPath + REPRESENTATION_FILE;
        String representationMetadataFilePath = folderPath + REPRESENTATION_METADATA_FILE;

        try {
            fileWriter = new FileWriter(representationFilePath);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    String.format("issues writing file %s: %s", representationFilePath,
                            e.getMessage()));
        }

        try {
            metadataFileWriter = new FileWriter(representationMetadataFilePath);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    String.format("issues writing file %s: %s", representationMetadataFilePath,
                            e.getMessage()));
        }
    }

    @Override
    public void serialize(String content) {
        try {
            fileWriter.write(content);
            fileWriter.flush();

            metadataFileWriter.write(REPRESENTATION_METADATA);
            metadataFileWriter.flush();
        } catch (IOException e) {
            throw new SerializatorException(e);
        }
    }
}
