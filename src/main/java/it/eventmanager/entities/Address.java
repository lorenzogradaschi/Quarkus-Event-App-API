package it.eventmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity@Table(name = "address")
public class Address extends PanacheEntity {
    public String street;
    public String city;
    public String state;
    public String zip;
    public String country;
    @JsonIgnore
    public Long id;

    /**
     * Secondo le annotazioni di JPA, per avere una relazione 1-1 , ovvero
     * 1 utente può avere 1 address
     * devo inserire la @OneToOne(mappedBy = "nometabella") sopra all'attributo
     */
    @OneToOne(mappedBy = "address")
    @JsonIgnore
    private User user;

    public Address() {

    }

    public String getZip() {
        return zip;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }
}
