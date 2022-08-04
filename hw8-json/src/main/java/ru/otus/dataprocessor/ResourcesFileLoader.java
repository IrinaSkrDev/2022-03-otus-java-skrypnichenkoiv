package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import ru.otus.model.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ResourcesFileLoader implements Loader {
    String fileName;
    private final ObjectMapper mapper = new ObjectMapper();

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        //читает файл, парсит и возвращает результат
        Gson gson = new Gson();
        var bufferedReader = new BufferedReader(new FileReader(this.fileName));
        String jsonString = bufferedReader.readLine();
        Measurement[] result = gson.fromJson(jsonString, Measurement[].class);
        // Type listType = new TypeToken<ArrayList<Measurement>>(){}.getType();
        //  List<Measurement> result = new Gson().fromJson(String.valueOf(this.file), listType);
        // var result = mapper.readValue(this.file, new TypeReference<List<Measurement>>() {
        // });
        //  mapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
        //Measurement[] arrayMensurement = mapper.readValue(this.file, Measurement[].class);
        // CollectionType javaType = mapper.getTypeFactory()
        //         .constructCollectionType(List.class, Measurement.class);
        // List<Measurement> result = mapper.readValue(this.file, javaType);
        return List.of(result);
    }
}
