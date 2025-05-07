package it.eventmanager.utilmapper;

import it.eventmanager.dto.EventDTO;
import it.eventmanager.entities.Event;

public class EventMapper {

    public static Event toEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.name);
        event.setDescription(eventDTO.description);
        event.setLocation(eventDTO.location);
        event.setParticipants(eventDTO.participants);
        event.setStartDate(eventDTO.startDate);
        event.setEndDate(eventDTO.endDate);
        event.setNumberParticipants(eventDTO.numberParticipants);
        event.setUser(eventDTO.user);
        return event;
    }


    public static EventDTO toDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.name = event.getName();
        eventDTO.description = event.getDescription();
        eventDTO.location = event.getLocation();
        eventDTO.participants = event.getParticipants();
        eventDTO.numberParticipants = event.getNumberParticipants();
        return eventDTO;
    }

}
