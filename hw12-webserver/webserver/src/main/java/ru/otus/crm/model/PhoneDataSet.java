package ru.otus.crm.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

@Entity
@Table(name = "phone_numbers")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Expose
    @Column(name = "number")
    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number, Client client) {
        this.number = number;
        this.client = client;
    }

    public PhoneDataSet(Long id, String number) {
        this.id = id;
        this.number = number;
    }

    @Override
    public PhoneDataSet clone() {
        return new PhoneDataSet(this.getId(), this.getNumber());
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return number;
    }
}
