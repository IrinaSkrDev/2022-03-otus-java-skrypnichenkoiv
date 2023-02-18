package ru.otus.crm.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "address")
public class Address {
    @Id
    private final Long addressId;
    private final String street;
    private final Long clientId;

    public Address(){
        this.addressId=null;
        this.street="";
        this.clientId=null;
    }
    @PersistenceCreator
    public Address(Long addressId, String street, Long clientId) {
        this.addressId=addressId;
        this.street = street;
        this.clientId = clientId;
    }
    public String getStreet() {
        return street;
    }

}
