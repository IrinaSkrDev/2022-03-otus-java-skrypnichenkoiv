package ru.otus.protobuf.service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RealDBServiceImpl implements RealDBService {

    private final List<Long> values;

    public RealDBServiceImpl() {
        values = new ArrayList<>();
    }

    @Override
    public void createListOfValue(Long lastValue) {
        for (int i = 0; i <= lastValue; i++) {
            values.add(Long.valueOf(i));
        }

    }

    @Override
    public List<Long> getValues() {
        return values;
    }
}
