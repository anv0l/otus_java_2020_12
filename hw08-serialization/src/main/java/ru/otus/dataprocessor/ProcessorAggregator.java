package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> result = new TreeMap<>();

        double totalValueByName;
        for (Measurement slice : data) {
            totalValueByName = 0;
            if (result.containsKey(slice.getName())) {
                totalValueByName = result.get(slice.getName());
            }
            result.put(slice.getName(), totalValueByName + slice.getValue());
        }

        return result;
    }
}
