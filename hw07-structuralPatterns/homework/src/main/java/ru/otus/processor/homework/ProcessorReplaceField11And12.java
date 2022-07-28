package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorReplaceField11And12 implements Processor {
    @Override
    public Message process(Message message){
        var copyField11 =  message.getField11();
        var copyField12 =  message.getField12();
        return message.toBuilder().field11(copyField12).field12(copyField11).build();
    }
}
