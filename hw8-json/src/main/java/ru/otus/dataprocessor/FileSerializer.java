package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    File file;
    private final ObjectMapper mapper = new ObjectMapper();

    public FileSerializer(String fileName) {
        this.file = new File(fileName);
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        //формирует результирующий json и сохраняет его в файл
        mapper.writeValue(this.file, data);
    }
}
