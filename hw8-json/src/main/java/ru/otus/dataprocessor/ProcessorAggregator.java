package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
/*
        Map<String, Double> result = data.stream()
                .collect(groupingBy((Measurement::getName), summingDouble(Measurement::getValue)));
        Map<String, Double> resulrCopy = new TreeMap<>();
        for (String key : result.keySet()) {
            resulrCopy.put(key, result.get(key));
        }
        return resulrCopy;*/

        return data.stream()
                .collect(groupingBy((Measurement::getName), TreeMap::new, summingDouble(Measurement::getValue)));
    }
}
