package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FileLoader implements Loader {
    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {
        //читает файл, парсит и возвращает результат
        String jsonData = getJsonFromFile();
        return getMeasurementListFromJson(jsonData);
    }

    private List<Measurement> getMeasurementListFromJson(String dataJson) {
        Gson gson = new Gson();
        List<Measurement> data;
        Type dataType = new TypeToken<ArrayList<Measurement>>(){}.getType();
        data = gson.fromJson(dataJson, dataType);
        return data;
    }

    private String getJsonFromFile() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (var dataFile = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while ((line = dataFile.readLine()) != null)
                stringBuilder.append(line);
        }
        return stringBuilder.toString();
    }
}
