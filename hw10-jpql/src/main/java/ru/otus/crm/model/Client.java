package ru.otus.crm.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "client")
@NoArgsConstructor
@ToString(exclude = "phones")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    //@LazyCollection(LazyCollectionOption.EXTRA)
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "client")
    private List<Phone> phones;


    public Client(String name) {
        this.id = null;
        this.name = name;
    }


    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address=address;
        this.phones=phones;
        for (Phone phone: phones){
            phone.setClient(this);
        }
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name,this.address,this.getPhones());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhones(List<Phone> phones){
        this.phones=phones;
        for (Phone phone: phones){
            phone.setClient(this);
        }
    }


    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
