package ru.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.lib.SensorDataBufferedWriter;
import ru.otus.api.SensorDataProcessor;
import ru.otus.api.model.SensorData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

// Этот класс нужно реализовать
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final NavigableMap<LocalDateTime, SensorData> dataBuffer = new ConcurrentSkipListMap<>();
    private final List<SensorData> bufferedData = new CopyOnWriteArrayList<>();

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
    }

    @Override
    public void process(SensorData data) {
        if (dataBuffer.size() >= bufferSize) {

            flush();
        } else {
            dataBuffer.put(data.getMeasurementTime(), data);
        }
    /*
        if (dataBuffer.size() >= bufferSize) {
            flush();
        }
    */
    }

    public void flush() {
        try {

            for (int i = 0; i < this.bufferSize; i++) {
                if (dataBuffer.firstEntry() != null) {
                    bufferedData.add(dataBuffer.firstEntry().getValue());
                    dataBuffer.pollFirstEntry();
                }
            }

            writer.writeBufferedData(bufferedData);
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
