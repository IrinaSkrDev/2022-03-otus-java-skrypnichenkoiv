package ru.otus.crm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table(name = "phone")
public class Phone {
    @Id
    private Long phoneId;
    private String number;

    @MappedCollection(idColumn = "client_id")
    private final Long clientId;

    public Phone(Long phoneId, String number, Long clientId) {
        this.phoneId = phoneId;
        this.number = number;
        this.clientId = clientId;
    }
    public Phone() {
        this.phoneId = null;
        this.number = null;
        this.clientId = null;
    }
    public String getPhone() {
        return number;
    }
}
