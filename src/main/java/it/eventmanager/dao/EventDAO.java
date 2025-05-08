package it.eventmanager.dao;

import it.eventmanager.entities.Event;
import it.eventmanager.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EventDAO {

    public List<Event> findAll(){
        return Event.listAll();
    }

    public List<Event> findByName(String name) {
        return Event.list("name = ?1", name);
    }

    public Event findById(Long id) {
        return Event.findById(id);
    }

    @Transactional
    public void persistEvent(Event event) {
        event.persist();
    }

    @Transactional
    public void deleteEvent(Event event) { event.delete();}

    @Transactional
    public void updateEventName(Event updatedEvent) {
        Event existingEvent = Event.findById(updatedEvent.id);
        if (existingEvent != null) {
            existingEvent.setName(updatedEvent.getName());
        }
    }

}
