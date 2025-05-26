package it.eventmanager.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import it.eventmanager.entities.Event;
import it.eventmanager.entities.User;
import it.eventmanager.exception.UserCustomException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.jboss.resteasy.annotations.Query;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static io.quarkus.hibernate.orm.panache.Panache.getEntityManager;

//CDI BEAN INJECTION, THIS CLASSS WILL BE INJECTABLE TROUGH CDI BEAN
//this happens trought the annotation @ApplicationScoped
//it's an AKA dao, perchè uso panache con i suoi metodi già esistenti
//quindi simulo quello che farebbe per davvero una connessione con JDBC e dietro i prepared statements.
@ApplicationScoped
public class UserDAO {

    int Public;

    public List<User> findAll(){
        return User.listAll();
    }

    public List<User> findByEmail(String email) {
        return User.list("email = ?1", email);
    }

    public User findById(Long id) {
        return User.findById(id);
    }

    public boolean isUserGreaterThan18(User user, int age){ return user.getAge() >= 18; }

    @Transactional
    public void persistUser(User user) { user.persist();}

    @Transactional
    public void deleteUser(User user) { user.delete(); }

    //usare gli stream con stream.filter()-> incontrare una determinata condizone
    public List<User> findUserByAFilter(List<User> users){
        return users.stream().filter(u -> u.getName().equals("Lorenzo")).collect(Collectors.toList());
    }

    public List<User> findUserByEmailFilter(List<User> users, String email){
        return users.stream().filter(u -> u.getEmail().equals(email)).collect(Collectors.toList());
    }

    public List<User> findUserByPredicate(List<User> users, String surname){
        return users.stream().filter(u -> u.getSurname().equals(surname)).collect(Collectors.toList());
    }

    public boolean areUsersGreaterThan18List(List<User> users, int age){
        return users.stream().anyMatch(user -> user.getAge() <= 18);
    }

    public boolean noneExistingUserInList(List<User> users, User user){
        return users.stream().noneMatch(u -> u.equals(user));
    }

    public boolean existingUserinList(List<User> users, User user){
        return users.stream().anyMatch(u -> u.equals(user));
    }

    public boolean checkinUserInListEvents(List<User> users, List<Event> events){
        return users.stream().anyMatch(usr -> events.contains(usr));
    }

    public boolean deMorganLawOnUser(User user){
        return !(user.getName().equals("Lorenzo") || (user.getSurname().equals("Gradaschi")));
    }

    public boolean isUserInNstedStream(List<Event> events, List<User> users){
        return events.stream().anyMatch(event -> users.stream().anyMatch(user -> user.getName().equals(event.getUser().getName())));
    }

    public boolean eventsThatDoNotContainsUsersInList(List<Event> events, List<User> users){
        return events.stream().allMatch(event -> users.stream().noneMatch(user -> user.getName().equals(event.getUser().getName())));
    }

    //filtro nella lista degli utenti un utente DENTRO UNO STREAM DI EVENTI CHE NON DEVE ESISTERE un utente uguale all'utente filtrato e lo converto in lista
    public List<User> userWithoutEvents(List<User> users, List<Event> events){
        return users.stream().filter(user -> events.stream().noneMatch(event -> event.getUser().getName().equals(user.getName()))).collect(Collectors.toList());
    }

    public List<User> eventListsss(List<User> users,  List<Event> events){
        return users.stream().filter(user -> events.stream().anyMatch(event -> event.getStartDate().equals(LocalDate.now()))).collect(Collectors.toList());
    }

}