package ru.otus.crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

@Entity
@Table(name = "phone")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Long phoneId;
    @Column(name = "phone_number")
    private String number;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id" , nullable=false)
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Client client;

    public Phone(Long phoneId, String number) {
        this.phoneId = phoneId;
        this.number = number;
    }

}
