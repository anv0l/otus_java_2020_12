package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Message> oldMessage = new HashMap<>();

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        Message savedMessage = new Message.Builder(oldMsg.getId())
                .field1(oldMsg.getField1())
                .field2(oldMsg.getField2())
                .field3(oldMsg.getField3())
                .field4(oldMsg.getField4())
                .field5(oldMsg.getField5())
                .field6(oldMsg.getField6())
                .field7(oldMsg.getField7())
                .field8(oldMsg.getField8())
                .field9(oldMsg.getField9())
                .field10(oldMsg.getField10())
                .field11(oldMsg.getField11())
                .field12(oldMsg.getField12())
                .field13(oldMsg.getField13())
                .build();

        oldMessage.put(oldMsg.getId(), savedMessage);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(oldMessage.get(id));
    }
}
