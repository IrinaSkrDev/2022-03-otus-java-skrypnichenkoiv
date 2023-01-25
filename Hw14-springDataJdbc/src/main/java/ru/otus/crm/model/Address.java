package ru.otus.crm.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "address")
public class Address {
    @Id
    private Long addressId;
    private String street;
    public Address() {
        this.addressId=null;
        this.street = null;
    }
    public Address(Long addressId,String street) {
        this.addressId=addressId;
        this.street = street;
    }
    public String getStreet() {
        return street;
    }

}
