package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table(name = "client")
public class Client implements Cloneable {

    @Id
    private final Long clientId;


    private final String name;


    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;


    public Client() {
        this.clientId = null;
        this.name = "";
        this.phones = null;
        this.address = null;

    }

    @PersistenceCreator
    public Client(Long clientId, String name, Address address, Set<Phone> phones) {
        this.clientId = clientId;
        this.name = name;
        this.address = address;
        this.phones = phones;

    }


    public Long getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public Address getAddress() {
        return address;
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + clientId +
                ", name='" + name + '\'' +
                '}';
    }
}
