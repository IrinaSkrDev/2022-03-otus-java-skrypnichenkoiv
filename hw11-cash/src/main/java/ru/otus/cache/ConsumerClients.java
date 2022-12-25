package ru.otus.cache;

import ru.otus.crm.model.Client;

public class ConsumerClients implements HwListener<Long, Client> {

    @Override
    public void notify(Long key, Client value, String action) {
        System.out.println("Добавлен клиент" + value);
    }
}
