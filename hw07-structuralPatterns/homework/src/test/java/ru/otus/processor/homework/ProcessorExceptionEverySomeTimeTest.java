package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProcessorExceptionEverySomeTimeTest {

    @Test
    void process() {
        var processorExceptionEverySomeTime = new ProcessorExceptionEverySomeTime(() -> LocalDateTime.of(2022, 07, 3, 5, 4, 5));
        var processorExceptionEverySomeTimeEx = new ProcessorExceptionEverySomeTime(() -> LocalDateTime.of(2022, 07, 3, 5, 4, 6));
        var message = new Message.Builder(1L).field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .build();
        assertThat(processorExceptionEverySomeTime.process(message)).isEqualTo(message);


        Exception exception = assertThrows(RuntimeException.class, () -> {  processorExceptionEverySomeTimeEx.process(message);});
        String expectedMessage = "Четная секунда!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}