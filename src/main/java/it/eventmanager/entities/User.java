package it.eventmanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.password4j.BcryptFunction;
import com.password4j.Hash;
import com.password4j.types.Bcrypt;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String dateOfBirth;
    private String gender;
    private int age;
    /**
     * secondo le annotazioni di jpa, invece quando devo andare sulla entity principale
     * per dare la relazione @OneToOne, devo dargli il tipo di cascade
     * dire la @JoinColumn a quale colonna devo fargli fare riferimento, e dargli la colonna di referenza
     * con refencedColumnName id
     * sopra l'attributo
     *
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    /**
     * per l'annotazione JPA @OneToMany, devo dire qual'è la tabella che mappa la tabella principale
     * sopra l'attributo della classe
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)

    @JsonIgnore
    private Set<Event> events;
    private String phone;

    public User(){}

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = encryptPassword(password);
    }

    public String encryptPassword(String password) {
        return BcryptUtil.bcryptHash(password);
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public Address getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User [name=" + name + ", surname=" + surname + ", email=" + email;
    }

}

