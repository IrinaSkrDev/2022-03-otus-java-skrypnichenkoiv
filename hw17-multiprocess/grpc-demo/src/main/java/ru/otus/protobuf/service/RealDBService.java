package ru.otus.protobuf.service;


import java.util.List;

public interface RealDBService {
    void createListOfValue(Long lastValue);

    List<Long> getValues();
}
