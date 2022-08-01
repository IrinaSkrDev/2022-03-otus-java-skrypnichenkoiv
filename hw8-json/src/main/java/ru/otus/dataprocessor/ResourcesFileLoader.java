package ru.otus.dataprocessor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.otus.model.Measurement;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    File file;
    private final ObjectMapper mapper = new ObjectMapper();

    public ResourcesFileLoader(String fileName) {
        this.file = new File(fileName);
    }

    @Override
    public List<Measurement> load() throws IOException {
        //читает файл, парсит и возвращает результат
        var result = mapper.readValue(this.file, new TypeReference<List<Measurement>>() {
        });
        //  mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        //Measurement[] arrayMensurement = mapper.readValue(this.file, Measurement[].class);
        // CollectionType javaType = mapper.getTypeFactory()
        //         .constructCollectionType(List.class, Measurement.class);
        // List<Measurement> result = mapper.readValue(this.file, javaType);
        return result;
    }
}
