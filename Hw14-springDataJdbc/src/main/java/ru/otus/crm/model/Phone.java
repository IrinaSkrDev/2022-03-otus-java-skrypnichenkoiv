package ru.otus.crm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table(name = "phone")
public class Phone {
    @Id
    private final Long phoneId;
    private final String number;


    private final Long clientId;
    public Phone() {
        this.phoneId = null;
        this.number = "";
        this.clientId = null;
    }
    @PersistenceCreator
    public Phone(Long phoneId, String number, Long clientId) {
        this.phoneId = phoneId;
        this.number = number;
        this.clientId = clientId;
    }

    public String getNumber() {
        return number;
    }
}
