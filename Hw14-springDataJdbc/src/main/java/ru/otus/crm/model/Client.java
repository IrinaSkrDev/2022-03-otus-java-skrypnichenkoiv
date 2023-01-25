package ru.otus.crm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.Set;

@Table(name = "client")
public class Client implements Cloneable {

    @Id
    private Long ClientId;


    private String name;


    @MappedCollection(idColumn = "address_id")
    private Address address;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phones;
    @Transient
    private final boolean isNew;

    public Client(String name) {
        this.ClientId = null;
        this.name = name;
        this.phones = null;
        this.address = null;
        this.isNew = true;
    }


    public Client() {
        this.ClientId = null;
        this.name = "";
        this.phones = null;
        this.address = null;
        this.isNew = true;

    }

    public Client(Long id, String name, Address address, Set<Phone> phones, boolean isNew) {
        this.ClientId = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
        this.isNew = isNew;

    }

    public Client(Long id, String name, Address address, Set<Phone> phones) {
        this(id, name, address, phones, false);
    }


    public Long getClientId() {
        return ClientId;
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
                "id=" + ClientId +
                ", name='" + name + '\'' +
                '}';
    }
}
