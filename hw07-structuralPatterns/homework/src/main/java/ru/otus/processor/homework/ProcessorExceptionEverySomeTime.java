package ru.otus.processor.homework;

import ru.otus.listener.homework.DateTimeProvider;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorExceptionEverySomeTime implements Processor {
    private final DateTimeProvider dateTimeProvider;

    @Override
    public Message process(Message message) {
        if (dateTimeProvider.getDate().getSecond() % 2 == 0) {
            throw new RuntimeException("Четная секунда!");
        }
        return message;
    }

    public ProcessorExceptionEverySomeTime(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }
}
