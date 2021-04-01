package ru.otus.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorExceptionOnEvenSecond;
import ru.otus.processor.homework.ProcessorSwapField11Field12;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ComplexProcessorHomeworkTest {
    @Test
    @DisplayName("ДЗ: Исключение на чётной секунде")
    void exceptionOnEvenSecondTest() {
        var processors = List.of(new ProcessorSwapField11Field12(),
                new ProcessorExceptionOnEvenSecond(LocalTime.of(10,10,12)));

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            if (ex.getClass() == DateTimeException.class && ex.getMessage().equals("Even second exception") ) {
                throw new TestException("Even second exception");
            }
        });

        var message = new Message.Builder(1L)
                .build();

        assertThatExceptionOfType(TestException.class)
                .isThrownBy(() -> complexProcessor.handle(message))
                .describedAs("Even second exception");
    }

    @Test
    @DisplayName("ДЗ: Нет исключения на нечётной секунде")
    void noExceptionOnOddSecondTest() {
        var processors =
                List.of(new ProcessorSwapField11Field12(),
                        new ProcessorExceptionOnEvenSecond(LocalTime.of(10,10,11)));

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            if (ex.getClass() == DateTimeException.class && ex.getMessage().equals("Even second exception")) {
                throw new TestException("Even second exception");
            }
        });

        var message = new Message.Builder(1L)
                .build();

        // no exception?
        complexProcessor.handle(message);
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}
