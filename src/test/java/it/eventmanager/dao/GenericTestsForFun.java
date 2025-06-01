package it.eventmanager.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import it.eventmanager.entities.Event;
import it.eventmanager.entities.User;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This is a class just for fun, to constantly develop my skills
 * in the free time, used for testing + writing and practice code
 */
class GenericTestsForFun {

    public static void main(String[] args) {
        GenericTestsForFun test = new GenericTestsForFun();

        User luca = new User();
        luca.setName("Luca");
        luca.setSurname("Mantovani");
        luca.setEmail("luca@gmail.com");
        luca.setPassword("pass123");
        luca.setDateOfBirth("2000-01-01");
        luca.setGender("Male");
        luca.setAge(25);

        User lorenzo = new User();
        lorenzo.setName("Lorenzo");
        lorenzo.setSurname("Gradaschi");
        lorenzo.setEmail("lorenzo@gmail.com");
        lorenzo.setPassword("pass456");
        lorenzo.setDateOfBirth("2005-04-12");
        lorenzo.setGender("Male");
        lorenzo.setAge(18);

        User sofia = new User();
        sofia.setName("Sofia");
        sofia.setSurname("Bianchi");
        sofia.setEmail("sofia@gmail.com");
        sofia.setPassword("sofiapass");
        sofia.setDateOfBirth("1995-07-23");
        sofia.setGender("Female");
        sofia.setAge(30);

        Event event1 = new Event();
        event1.setName("Tech Talk");
        event1.setDescription("Talk on AI & Java");
        event1.setLocation("Padova");
        event1.setUser(luca); // created by luca
        event1.setParticipants(new HashSet<>(Set.of(luca, lorenzo)));
        event1.setNumberParticipants(2);

        Event event2 = new Event();
        event2.setName("Hackathon");
        event2.setDescription("Coding event 24h");
        event2.setLocation("Milano");
        event2.setUser(luca); // created by luca
        event2.setParticipants(new HashSet<>(Set.of(luca, sofia)));
        event2.setNumberParticipants(2);

        Event event3 = new Event();
        event3.setName("Workshop UX");
        event3.setDescription("Design thinking");
        event3.setLocation("Torino");
        event3.setUser(sofia); // created by sofia
        event3.setParticipants(new HashSet<>(Set.of(sofia)));
        event3.setNumberParticipants(1);

        luca.setEvents(new HashSet<>(Set.of(event1, event2)));
        sofia.setEvents(new HashSet<>(Set.of(event3)));
        lorenzo.setEvents(new HashSet<>()); // nessun evento creato da lui

        List<User> usersList = List.of(luca, lorenzo, sofia);
        Set<Event> eventsSet = Set.of(event1, event2, event3);

        System.out.println("\n--- Extracted Events ---");
        test.extracted(eventsSet, luca).forEach(System.out::println);

        System.out.println("\n--- Optional Check ---");
        Optional<User> optionalUser = Optional.of(luca);
        optionalUser.stream().filter(u -> u.getDateOfBirth().equals("2000-01-01"))
                .findAny().ifPresent(System.out::println);

        System.out.println("\n--- User Details ---");
        test.listOfUserNameAndSurnameAndAgeAndPassword(usersList).forEach(System.out::println);

        System.out.println("\n--- Remove Duplicates ---");
        test.removeDuplicateList(usersList).forEach(System.out::println);

        System.out.println("\n--- Name Set ---");
        usersList.stream().map(User::getName).collect(Collectors.toSet()).forEach(System.out::println);

        System.out.println("\n--- FlatMapped Sample ---");
        List<List<String>> nestedList = List.of(List.of("a", "b"), List.of("c"), List.of("d", "e"));
        nestedList.stream().flatMap(List::stream).toList().forEach(System.out::println);

        System.out.println("\n--- Filter by Email ---");
        test.filterByEmail(usersList).forEach(System.out::println);

        System.out.println("\n--- Name and Surname Only ---");
        test.listOfUserNamesAndSurname(usersList).forEach(System.out::println);

        System.out.println("\n--- All have Gmail? ---");
        System.out.println(test.isListFilteredUserByEmail(usersList));

        System.out.println("\n--- Events belong to their users? ---");
        test.listEvents(usersList).forEach(System.out::println);

        System.out.println("\n--- Any user has at least one event? ---");
        test.operationOnStreams(usersList).forEach(System.out::println);

        System.out.println("\n--- Any user > 18? ---");
        System.out.println(test.doIhaveUsersOlderThan18(usersList));

        System.out.println("\n--- All events from all users ---");
        test.extractEventsList(usersList).forEach(System.out::println);

        System.out.println("\n--- Users not owning their events ---");
        test.filterUserNotBelongingToStreamOfEvents(usersList).forEach(System.out::println);

        System.out.println("\n--- Predicate >= 18 ---");
        System.out.println(test.predicate(usersList));

        System.out.println("\n--- Filter out nulls ---");
        test.genericOperationOnList(Arrays.asList(luca, null, sofia)).forEach(System.out::println);

        System.out.println("\n--- Filter by Gender = Male ---");
        test.filterUserByGender(usersList, "Male").forEach(System.out::println);

        System.out.println("\n--- All emails ---");
        test.transformUsersInStringList(usersList).forEach(System.out::println);

        System.out.println("\n--- Sort by Age DESC ---");
        test.sortingByAge(usersList).forEach(System.out::println);

        System.out.println("\n--- Limit to first 2 users ---");
        test.limitedByXUserList(usersList).forEach(System.out::println);

        System.out.println("\n--- Count users >= 18 ---");
        System.out.println(test.countOccurenciesOfAdultsInList(usersList));

        System.out.println("\n--- Any user >= 18? ---");
        System.out.println(test.areExistingAny18YearsOldInUserList(usersList));

        System.out.println("\n--- Predicate event.user == user ---");
        System.out.println(test.predicateOfNewXYZCondition(new ArrayList<>(eventsSet), luca));

        System.out.println("\n--- Extract first event from luca ---");
        test.extractEventWithUserInList(new ArrayList<>(eventsSet), luca).ifPresent(System.out::println);

        System.out.println("\n--- Users with NO participation ---");
        test.extractUserWithNonParticipatingEvent(usersList).forEach(System.out::println);

        System.out.println("\n--- Users with ALL events where they participate ---");
        test.extractUserThatMatchesInEvent(usersList).forEach(System.out::println);

        System.out.println("\n--- First event with name != null ---");
        test.extractEventFromUser(usersList).ifPresent(System.out::println);

        System.out.println("\n--- Extract from all events ---");
        test.extractUserFromEvents(usersList).ifPresent(System.out::println);

        Set<String> mySet = new HashSet<>(Set.of("a", "b", "c"));
        System.out.println(mySet);
        mySet.add("d");

        List<String> listWithDuplicatest = List.of("a","b","c", "a");
        Set<String> uniqueList = new HashSet<>(listWithDuplicatest);
        System.out.println(uniqueList);

        Optional<String> first = mySet.stream().findFirst();
        first.isPresent();

        Map<String, User> userMap = new HashMap<>();
        List<User> users = List.of(luca, sofia, lorenzo);

        users.forEach(user ->{
            userMap.put(user.getName(), user);
        });
        users.forEach(user ->{
            userMap.put(user.getEmail(), user);
        });

        Map<String, String> source = Map.of("one", "uno", "two", "due");

        Map<String, String> destination = new HashMap<>();
        source.forEach((key, value)->{
            destination.put(key,value);
        });

        Map<String, Integer> mappa2 = new HashMap<>();
        users.forEach((user) -> {
            mappa2.put(user.getEmail(), user.getAge());
        });

        List<User> onlyMaggiorenni = users.stream().filter(user -> user.getAge()>=18).toList();
        Map<User, Integer> mappaMaggiorenni = new HashMap<>();
        onlyMaggiorenni.forEach(user -> {
            mappaMaggiorenni.put(user, user.getAge());
        });

        mappaMaggiorenni.forEach((user, age)->{
            System.out.println(user + "" + age);
        });

        Map.of("key", users.get(0));

        Map<Integer, List<User>> mappaDiUtentiNumerati = new HashMap<>();
        mappaDiUtentiNumerati.forEach((intero, userss)->{
            if(userss.stream().anyMatch(usr -> usr.getEmail() != null)){
                System.out.println("Email non è nulla all'utente numero" + intero);
            }
        });

        System.out.println(checkPalindrome("anna"));
    }

    public static boolean checkPalindrome(String input){
        StringBuilder stringBuilder = new StringBuilder(input);
        StringBuilder reversed = stringBuilder.reverse();
        return input.contentEquals(reversed);
    }

    public List<List<Integer>> raggruppaPerSomma(List<Integer> input) {
        Map<Integer, List<Integer>> mappa = new HashMap<>();

        input.forEach((value) -> {
            int numero = value;
            int somma = 0;
            while (numero > 0) {
                somma += numero % 10;
                numero /= 10;
            }

            mappa.putIfAbsent(somma, new ArrayList<>());
            mappa.get(somma).add(value);
        });

        for (List<Integer> gruppo : mappa.values()) {
            Collections.sort(gruppo);
        }

        List<List<Integer>> risultato = new ArrayList<>();

        mappa.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> risultato.add(entry.getValue()));

        return risultato;
    }

    //wrapper or method found on stackoverflow
    public static String formatCreditCardString(String creditCard){
        return padded(creditCard, 4, "-");
    }

    //reference stack overflow: https://stackoverflow.com/questions/537174/putting-char-into-a-java-string-for-each-n-characters
    public static String padded(String original, int interval, String separator) {
        String formatted = "";
        for(int i = 0; i < original.length(); i++) {
            if (i % interval == 0 && i > 0) {
                formatted += separator;
            }
            formatted += original.substring(i, i+1);
        }
        return formatted;
    }

    public static char findNonRepetitiveChar(String inputString){
        boolean found = false;
        char c = '\0';
        for(int i = 0; i < inputString.length(); i++){
            for(int j = 0; j < inputString.length(); j++){
                if(i != j && inputString.charAt(i) == inputString.charAt(j)){
                    found = true;
                    c = inputString.charAt(i);
                    break;
                }
            }
            if(!found){
                return inputString.charAt(i);
            }
        }
        return c;
    }

    public boolean isValidJson(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        try{
            mapper.readTree(jsonString);
            return true;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkThatValueIsInJson(String jsonString,Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonString);
        return jsonNode.has((String) obj);
    }

    public List<String> createJavaListFromJSonString(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new JsonMapper();
        return mapper.readValue(jsonString, new TypeReference<List<String>>() {});
    }

    public List<User> getUserListFromJsonString(String jsonString) throws JsonProcessingException {
        ObjectMapper mapper = new JsonMapper();
        return mapper.readValue(jsonString, new TypeReference<List<User>>() {});
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
