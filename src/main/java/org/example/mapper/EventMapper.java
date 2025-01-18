package org.example.mapper;

import org.example.dto.EventDTO;
import org.example.model.Event;
import org.example.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class EventMapper {

    public static EventDTO toDTO(Event event) {
        return new EventDTO(
            event.getName(),
            event.getLocation(),
            event.getParticipants(),
            event.getCreator() != null ? event.getCreator().id : null,
            event.getAttendees() != null ? event.getAttendees().stream().map(user -> user.id).collect(Collectors.toList()) : null
        );
    }

    public static Event toEntity(EventDTO dto, User creator, List<User> attendees) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setLocation(dto.getLocation());
        event.setParticipants(dto.getParticipants());
        event.setCreator(creator);
        event.setAttendees(attendees);
        return event;
    }
}
