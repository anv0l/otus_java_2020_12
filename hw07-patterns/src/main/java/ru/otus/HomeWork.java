package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.homework.ProcessorExceptionOnEvenSecond;
import ru.otus.processor.homework.ProcessorSwapField11Field12;

import java.time.DateTimeException;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяьться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) {
        var processors = List.of(new ProcessorSwapField11Field12(),
                new ProcessorExceptionOnEvenSecond());

        var complexProcessor = new ComplexProcessor(processors, (ex) -> {
            if (ex.getClass() == DateTimeException.class && ex.getMessage().equals("Even second exception") ) {
                System.out.println("Even second reached.");}});

        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(List.of("field13"));
        var message = new Message.Builder(1L)
                .field11("field11")
                .field12("field12")
                .field13(objectForMessage)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
    }
}
