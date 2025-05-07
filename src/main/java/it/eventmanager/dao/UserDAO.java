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

    public List<User> findAll(){
        return User.listAll();
    }

    public List<User> findByEmail(String email) {
        return User.list("email = ?1", email);
    }

    public User findById(Long id) {
        return User.findById(id);
    }

    public boolean isUserGreaterThan18(User user, int age){
        return user.getAge() >= 18;
    }

    @Transactional
    public void persistUser(User user) {
        user.persist();
    }

    @Transactional
    public void deleteUser(User user) {
        user.delete();
    }

    public static User findUserWithMostAttendedEvents() {
        //TODO usare i criteria query
        return (User) getEntityManager()
                .createNativeQuery(" SELECT u.* FROM users u  JOIN event_participants ep ON u.id = ep.user_id GROUP BY u.id ORDER BY COUNT(ep.event_id) DESC LIMIT 1 ", User.class)
                .getSingleResult();
    }

    //TODO USARE GLI STREAM !

    /*
     * // Intermediate Operations
     * stream.filter()           // Keep elements that match condition
     * stream.map()              // Transform each element individually
     * stream.flatMap()          // Flatten nested stream elements
     * stream.distinct()         // Remove all duplicate elements
     * stream.sorted()           // Sort elements in natural order
     * stream.sorted(Comparator) // Sort elements with custom order
     * stream.peek()             // Perform action without changing stream
     * stream.limit(n)           // Keep only first n elements
     * stream.skip(n)            // Ignore first n elements
     * stream.mapToInt()         // Convert stream to IntStream
     * stream.mapToDouble()      // Convert stream to DoubleStream
     * stream.mapToLong()        // Convert stream to LongStream
     * stream.boxed()            // Box primitives into wrapper objects
     *
     * // Terminal Operations
     * stream.forEach()          // Perform action on each element
     * stream.forEachOrdered()   // Ordered version of forEach()
     * stream.toArray()          // Convert stream to array
     * stream.reduce()           // Combine elements into single result
     * stream.collect()          // Gather stream into collection/result
     * stream.min()              // Smallest element using comparator
     * stream.max()              // Largest element using comparator
     * stream.count()            // Number of elements in stream
     * stream.anyMatch()         // Any element matches condition?
     * stream.allMatch()         // All elements match condition?
     * stream.noneMatch()        // No elements match condition?
     * stream.findFirst()        // Get first element if present
     * stream.findAny()          // Get any element if present
     *
     * // Stream Creation (Factories)
     * Stream.of()               // Create stream from given values
     * Stream.empty()            // Create an empty stream
     * Stream.generate()         // Create infinite stream (supplier)
     * Stream.iterate()          // Create stream from seed/function
     * Arrays.stream()           // Create stream from array
     * Collection.stream()       // Stream from list/set/collection
     * IntStream.range()         // Range of int values
     * IntStream.rangeClosed()   // Inclusive range of ints
     *
     * // Closing Streams
     * stream.close()            // Manually close stream resource
     * stream.isParallel()       // Check if stream is parallel
     * stream.parallel()         // Convert to parallel stream
     * stream.sequential()       // Convert to sequential stream
     * stream.unordered()        // Remove encounter order if present
     */


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




}