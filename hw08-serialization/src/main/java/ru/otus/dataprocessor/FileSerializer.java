package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        //формирует результирующий json и сохраняет его в файл
        Gson gson = new Gson();

        String json = gson.toJson(data);

        try (var bufferedWriter = new BufferedWriter(new FileWriter(this.fileName))) {
            bufferedWriter.write(json);
        }
    }
}
