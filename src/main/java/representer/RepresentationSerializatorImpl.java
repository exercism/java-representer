package representer;

import java.io.FileWriter;
import java.io.IOException;

public class RepresentationSerializatorImpl implements RepresentationSerializator {
    private static final String REPRESENTATION_FILE = "representation.txt";
    private String folderPath;
    private String representationFilePath;
    private FileWriter fileWriter;

    public RepresentationSerializatorImpl(String folderPath) {
        this.folderPath = folderPath;
        representationFilePath = folderPath + REPRESENTATION_FILE;
        try {
            fileWriter = new FileWriter(representationFilePath);
        } catch (IOException e) {
            throw new IllegalArgumentException(
                    String.format("issues writing file %s: %s", representationFilePath,
                            e.getMessage()));
        }
    }

    @Override
    public void serialize(String content) {
        try {
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException e) {
            throw new SerializatorException(e);
        }
    }

    public static String getRepresentationFile() {
        return REPRESENTATION_FILE;
    }

    public String getFolderPath() {
        return folderPath;
    }



}
