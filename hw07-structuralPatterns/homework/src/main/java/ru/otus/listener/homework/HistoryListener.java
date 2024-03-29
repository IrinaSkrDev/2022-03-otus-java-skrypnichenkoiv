package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private final List<Message> messageList = new ArrayList<Message>();

    @Override
    public void onUpdated(Message msg) {
        ObjectForMessage objectForMessage = new ObjectForMessage();
        List<String> listData = new ArrayList<>();
        if (msg.getField13() != null) {
            for (int i = 0; i < msg.getField13().getData().size(); i++) {
                listData.add(msg.getField13().getData().get(i));
            }
            objectForMessage.setData(listData);
        }

        var msgCopy = new Message.Builder(msg.getId())
                .field1(msg.getField1())
                .field2(msg.getField2())
                .field3(msg.getField3())
                .field4(msg.getField4())
                .field5(msg.getField5())
                .field6(msg.getField6())
                .field7(msg.getField7())
                .field8(msg.getField8())
                .field9(msg.getField9())
                .field10(msg.getField10())
                .field11(msg.getField11())
                .field12(msg.getField12())
                .field13(objectForMessage)
                .build();
        this.messageList.add(msgCopy);
        //throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        Optional<Message> messageOptional = messageList.stream().filter(message -> message.getId() == id).findFirst();
        return messageOptional;
        // throw new UnsupportedOperationException();
    }
}
