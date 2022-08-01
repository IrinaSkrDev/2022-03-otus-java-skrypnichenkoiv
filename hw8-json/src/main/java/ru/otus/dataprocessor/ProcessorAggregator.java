package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        Map<String, Double> result = data.stream()
                .collect(groupingBy(Measurement::getName, summingDouble(Measurement::getValue)));
        return result;
    }
}
