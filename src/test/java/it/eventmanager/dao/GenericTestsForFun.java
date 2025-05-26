package it.eventmanager.dao;

import it.eventmanager.entities.Event;
import it.eventmanager.entities.User;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GenericTestsForFun {

    public static void main(String[] args) {
        GenericTestsForFun test = new GenericTestsForFun();

        User user = new User();
        user.setName("Luca");
        user.setSurname("Mantovani");
        user.setEmail("test.email1@gmail.com");
        user.setPassword("mystrongpassword");
        user.setDateOfBirth("29-03-2003");
        user.setAge(19);
        user.setGender("Male");

        User user1 = new User();
        user1.setName("Lorenzo");
        user1.setEmail("test.email2@gmail.com");
        user1.setSurname("Gradaschi");
        user1.setPassword("thestrongestpasswordeverexistedbeforeinthelife!!");
        user1.setDateOfBirth("29-03-2003");
        user1.setGender("Male");
        user1.setAge(11);

        Set<Event> events = new HashSet<>();
        Event event = new Event();
        event.setName("Event1");
        event.setNumberParticipants(10);
        event.setUser(user);
        events.add(event);
        user.setEvents(events);

        List<User> usersList = new ArrayList<>();
        usersList.add(user);
        usersList.add(user1);

        System.out.println("\n--- Extracted Events ---");
        Set<Event> extractedSet = test.extracted(events, user);
        extractedSet.forEach(System.out::println);

        System.out.println("\n--- Optional Check ---");
        Optional<User> optionalUser1 = Optional.of(user);
        Optional<User> newOptionalFiltered = optionalUser1.stream()
                .filter(usr -> usr.getDateOfBirth().equals("29-03-2003"))
                .findAny();
        System.out.println("Is DateOfBirth filtered Optional present? " + newOptionalFiltered.isPresent());

        System.out.println("\n--- User Details ---");
        List<String> usersDetails = test.listOfUserNameAndSurnameAndAgeAndPassword(usersList);
        usersDetails.forEach(System.out::println);

        System.out.println("\n--- Remove Duplicates ---");
        List<User> usersDuplicateFilteredRemoved = test.removeDuplicateList(usersList);
        usersDuplicateFilteredRemoved.forEach(System.out::println);

        System.out.println("\n--- Name Set ---");
        Set<String> nameSet = usersList.stream().map(User::getName).collect(Collectors.toSet());
        nameSet.forEach(System.out::println);

        System.out.println("\n--- FlatMapped List ---");
        List<List<String>> nestedList = List.of(
                List.of("a", "b"), List.of("c", "d"), List.of("e")
        );
        List<String> flatMappedList = nestedList.stream().flatMap(List::stream).toList();
        flatMappedList.forEach(System.out::println);

        System.out.println("\n--- Filter by Email ---");
        test.filterByEmail(usersList).forEach(System.out::println);

        System.out.println("\n--- Name and Surname Only ---");
        test.listOfUserNamesAndSurname(usersList).forEach(System.out::println);

        System.out.println("\n--- Email Filter Match? ---");
        System.out.println(test.isListFilteredUserByEmail(usersList));

        System.out.println("\n--- Events belong to their users? ---");
        test.listEvents(usersList).forEach(System.out::println);

        System.out.println("\n--- Any user has any event? ---");
        test.operationOnStreams(usersList).forEach(System.out::println);

        System.out.println("\n--- Any user older than 18? ---");
        System.out.println(test.doIhaveUsersOlderThan18(usersList));

        System.out.println("\n--- Events from all users ---");
        test.extractEventsList(usersList).forEach(System.out::println);

        System.out.println("\n--- Users not belonging to their own events ---");
        test.filterUserNotBelongingToStreamOfEvents(usersList).forEach(System.out::println);

        System.out.println("\n--- Predicate check >= 18? ---");
        System.out.println(test.predicate(usersList));

        System.out.println("\n--- Generic filter nulls ---");
        test.genericOperationOnList(Arrays.asList(user, null, user1)).forEach(System.out::println);

        System.out.println("\n--- Filter by gender Male ---");
        test.filterUserByGender(usersList, "Male").forEach(System.out::println);

        System.out.println("\n--- User emails ---");
        test.transformUsersInStringList(usersList).forEach(System.out::println);

        System.out.println("\n--- Sorted by Age DESC ---");
        test.sortingByAge(usersList).forEach(System.out::println);

        System.out.println("\n--- Limit to 1st 5 Users ---");
        test.limitedByXUserList(usersList).forEach(System.out::println);

        System.out.println("\n--- Count >=18 ---");
        System.out.println(test.countOccurenciesOfAdultsInList(usersList));

        System.out.println("\n--- Any >=18? ---");
        System.out.println(test.areExistingAny18YearsOldInUserList(usersList));

        System.out.println("\n--- Events matching User ---");
        System.out.println(test.predicateOfNewXYZCondition(new ArrayList<>(events), user));

        System.out.println("\n--- Extract first event with User ---");
        test.extractEventWithUserInList(new ArrayList<>(events), user).ifPresent(System.out::println);

        System.out.println("\n--- Users with NO event participation ---");
        test.extractUserWithNonParticipatingEvent(usersList).forEach(System.out::println);

        System.out.println("\n--- Users matching ALL event participations ---");
        test.extractUserThatMatchesInEvent(usersList).forEach(System.out::println);

        System.out.println("\n--- Extract first non-null event from users ---");
        test.extractEventFromUser(usersList).ifPresent(System.out::println);

        System.out.println("\n--- Extract event with user from all events ---");
        test.extractUserFromEvents(usersList).ifPresent(System.out::println);
    }

    public List<User> filterByEmail(List<User> users) {
        return users.stream()
                .filter(user -> user.getEmail().endsWith("@gmail.com"))
                .collect(Collectors.toList());
    }

    public List<String> listOfUserNamesAndSurname(List<User> users) {
        return users.stream()
                .flatMap(user -> Stream.of(user.getName(), user.getSurname()))
                .toList();
    }

    public List<String> listOfUserNameAndSurnameAndAgeAndPassword(List<User> users) {
        return users.stream()
                .flatMap(user -> Stream.of(
                        user.getName(),
                        user.getSurname(),
                        user.getPassword(),
                        String.valueOf(user.getAge())))
                .toList();
    }

    public boolean isListFilteredUserByEmail(List<User> listUsers) {
        return listUsers.stream()
                .map(User::getEmail)
                .allMatch(email -> email.contains("@gmail.com"));
    }

    public List<User> listEvents(List<User> users) {
        return users.stream()
                .filter(user -> user.getEvents().stream()
                        .allMatch(event -> event.getUser().equals(user)))
                .toList();
    }

    public List<User> operationOnStreams(List<User> list) {
        return list.stream()
                .filter(user -> user.getEvents().stream()
                        .anyMatch(event -> event.getUser().equals(user)))
                .toList();
    }

    public boolean doIhaveUsersOlderThan18(List<User> list) {
        return list.stream().anyMatch(user -> user.getAge() > 18);
    }

    private static Set<Event> extracted(Set<Event> eventList, User user1) {
        return eventList.stream()
                .filter(event -> event.getUser().equals(user1))
                .collect(Collectors.toSet());
    }

    private List<Event> extractEventsList(List<User> users) {
        return users.stream()
                .flatMap(user -> user.getEvents().stream())
                .toList();
    }

    private List<User> filterUserNotBelongingToStreamOfEvents(List<User> users) {
        return users.stream()
                .filter(user -> user.getEvents().stream()
                        .noneMatch(event -> event.getUser().equals(user)))
                .toList();
    }

    private List<User> removeDuplicateList(List<User> list) {
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }

    public static boolean isUserGreaterOrEqualThan18(User user) {
        return user.getAge() >= 18;
    }

    private boolean predicate(List<User> users) {
        return users.stream()
                .anyMatch(GenericTestsForFun::isUserGreaterOrEqualThan18);
    }

    private <T> List<T> genericOperationOnList(List<T> genericListToOperateOn) {
        return genericListToOperateOn.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<User> filterUserByGender(List<User> usersList, String gender) {
        return usersList.stream()
                .filter(user -> user.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public List<String> transformUsersInStringList(List<User> usersList) {
        return usersList.stream()
                .map(User::getEmail)
                .toList();
    }

    public List<User> sortingByAge(List<User> list) {
        return list.stream()
                .sorted(Comparator.comparing(User::getAge).reversed())
                .collect(Collectors.toList());
    }

    public List<User> limitedByXUserList(List<User> users) {
        return users.stream()
                .limit(5)
                .toList();
    }

    public long countOccurenciesOfAdultsInList(List<User> users) {
        return users.stream()
                .filter(user -> user.getAge() >= 18)
                .count();
    }

    public boolean areExistingAny18YearsOldInUserList(List<User> list) {
        return list.stream()
                .anyMatch(user -> user.getAge() >= 18);
    }

    public boolean predicateOfNewXYZCondition(List<Event> events, User user) {
        return events.stream()
                .anyMatch(event -> event.getUser().equals(user));
    }

    public Optional<Event> extractEventWithUserInList(List<Event> events, User user) {
        return events.stream()
                .filter(event -> event.getUser().equals(user))
                .findFirst();
    }

    public Set<User> extractUserWithNonParticipatingEvent(List<User> users) {
        return users.stream()
                .filter(user -> user.getEvents().stream()
                        .noneMatch(event -> event.getParticipants().contains(user)))
                .collect(Collectors.toSet());
    }

    public Set<User> extractUserThatMatchesInEvent(List<User> users) {
        return users.stream()
                .filter(user -> user.getEvents().stream()
                        .allMatch(event -> event.getParticipants().contains(user)))
                .collect(Collectors.toSet());
    }

    public Optional<Event> extractEventFromUser(List<User> usersList) {
        return usersList.stream()
                .flatMap(user -> user.getEvents().stream())
                .filter(event -> event.getName() != null)
                .findFirst();
    }

    public Optional<Event> extractUserFromEvents(List<User> list) {
        return list.stream()
                .flatMap(user -> user.getEvents().stream())
                .filter(event -> event.getName() != null)
                .findFirst();
    }
}
