package ru.otus.crm.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column
    @Expose
    private String street;

    @OneToOne(mappedBy = "address")
    private Client client;

    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public AddressDataSet(Long id, String street) {
        this.id = id;
        this.street = street;
    }

    @Override
    public AddressDataSet clone() {
        return new AddressDataSet(this.getId(), this.getStreet());
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setStreet(String street, Client client) {
        this.street = street;
        this.client = client;
    }

    public String getStreet() {
        return street;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return street;
    }

}
