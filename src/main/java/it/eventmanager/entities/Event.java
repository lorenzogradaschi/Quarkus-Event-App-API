package it.eventmanager.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "event")
public class Event extends PanacheEntity {

    private String name;
    private String description;
    private String location;

    /**
     * Siccome devo avere una referenza
     * @OneToMany e referenza @ManyToOne, devo dire nella classe che è interessata alla ManyToOne di avere
     * un attributo della classe utente, referenziata alla colonna user_id
     */
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

    /**
     * per avere il join di una tabella invece many_to_many,
     * ho bisogno dell'annotazione @JoinTable, e specificare il nome della tabella , fare il join delle colonne
     * e fare il reverse delle column
     */
    @ManyToMany
    @JoinTable(
            name = "event_participants",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> participants;

    private Date startDate;
    private Date endDate;
    private Integer numberParticipants;

    public Event(){}

    public Event(String name, String description, String location, User user, Set<User> participants, Date startDate, Date endDate, Integer numberParticipants) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.user = user;
        this.participants = participants;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberParticipants = numberParticipants;
    }


    public Integer getNumberParticipants() {
        return numberParticipants;
    }

    public void setNumberParticipants(Integer numberParticipants) {
        this.numberParticipants = numberParticipants;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Event name = " + name + "Event Description =  " + description + "Number of participants=  " + numberParticipants + "List of Users attendance =  " +  participants;
    }

}
