package representer;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MappingSerializatorImpl implements MappingSerializator {
    private String folderPath;
    private String mappingFilePath;
    private FileWriter fileWriter;

    private static final String MAPPING_FILE = "mapping.txt";

    public MappingSerializatorImpl(String folderPath) {
        this.folderPath = folderPath;
        mappingFilePath = folderPath + MAPPING_FILE;
        try {
            fileWriter = new FileWriter(mappingFilePath);
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("issues writing file %s: %s",
                    mappingFilePath, e.getMessage()));
        }
    }

    @Override
    public void serialize(Map<String, String> mapping) {
        String json = jsonSerialization(mapping);
        try {
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            throw new SerializatorException(e);
        }
    }

    private String quote(String content) {
        return String.format("\"%s\"", content);
    }

    private String serializeEntry(Entry<String, String> entry) {
        return String.format("%s : %s", quote(entry.getKey()), quote(entry.getValue()));
    }
    private String jsonSerialization(Map<String, String> mapping) {
        StringBuffer jsonString = new StringBuffer("{\n");
        jsonString.append(String.join(",\n", mapping.entrySet().stream()
                .map(placeholder -> serializeEntry(placeholder)).collect(Collectors.toList())));
        jsonString.append("\n}");
        return jsonString.toString();
    }

    public String getFolderPath() {
        return folderPath;
    }

    public static String getMappingFile() {
        return MAPPING_FILE;
    }
}
