package ru.otus.crm.model;

import com.google.gson.annotations.Expose;
import jdk.jfr.Experimental;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "client")
public class Client implements Cloneable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @Expose
    private Long id;

    @Expose
    @Column(name = "name")
    private String name;

    @Expose
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressDataSet address;

    @Expose
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<PhoneDataSet> phones;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name, AddressDataSet address, List<PhoneDataSet> phoneDataSet) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phoneDataSet;
    }

    @Override
    public Client clone() {
        var client = new Client(this.getId(), this.getName());
        client.setAddress(this.getAddress().clone());
        client.setPhones(new ArrayList<>(this.getPhones().stream()
                .map(PhoneDataSet::clone)
                .collect(Collectors.toList())));
        return this;
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

    public AddressDataSet getAddress() {
        return new AddressDataSet(address.getId(), address.getStreet());
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return new ArrayList<>(this.phones) ;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address = '" + address.getStreet() + "'" +
                ", phone(s) = '" + Arrays.toString(phones.toArray()) + "'" +
                '}';
    }
}
