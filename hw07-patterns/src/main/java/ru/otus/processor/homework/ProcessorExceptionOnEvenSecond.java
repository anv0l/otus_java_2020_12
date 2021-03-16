package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.DateTimeException;
import java.time.LocalTime;

public class ProcessorExceptionOnEvenSecond implements Processor {
    private LocalTime date;

    public ProcessorExceptionOnEvenSecond() {}

    public ProcessorExceptionOnEvenSecond(LocalTime localTime) {
        this.date = localTime;
    }

    @Override
    public Message process(Message message) {
        if (this.date == null) {
            this.date = LocalTime.now();
        }
        System.out.println(this.date.getSecond()); // debug
        if (this.date.getSecond() % 2 == 0) {
            throw new DateTimeException("Even second exception");
        }
        return message;
    }
}