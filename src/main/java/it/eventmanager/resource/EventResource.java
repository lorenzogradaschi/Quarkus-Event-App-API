package it.eventmanager.resource;

import it.eventmanager.dao.EventDAO;
import it.eventmanager.dto.EventDTO;
import it.eventmanager.entities.Event;
import it.eventmanager.exception.EventCustomException;
import it.eventmanager.utilmapper.EventMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/events")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventResource {

    @Inject
    EventDAO eventDAO;

    @GET
    @Operation(summary = "Get all events", description = "Returns a list of all events")
    @APIResponse(responseCode = "200", description = "List of events")
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventDAO.findAll();
        return events.stream()
                .map(EventMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/by-name")
    @Operation(summary = "Get events by name", description = "Returns a list of events matching the given name")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "List of events with matching name"),
            @APIResponse(responseCode = "404", description = "Event not found")
    })
    public List<EventDTO> getEventByName(
            @Parameter(name = "name", description = "Name of the event", required = true)
            @QueryParam("name") String name) throws SQLException, EventCustomException {
        try {
            List<Event> events = eventDAO.findByName(name);
            return events.stream()
                    .map(EventMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (EventCustomException e) {
            throw new EventCustomException("Event not found", e);
        }
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get event by ID", description = "Returns a single event by its ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Event found"),
            @APIResponse(responseCode = "404", description = "Event not found")
    })
    public Response getEventById(
            @Parameter(name = "id", description = "ID of the event", required = true)
            @PathParam("id") Long id) throws EventCustomException {
        Event event = eventDAO.findById(id);
        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Event not found").build();
        }
        return Response.ok(EventMapper.toDTO(event)).build();
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new event", description = "Creates a new event")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Event created"),
            @APIResponse(responseCode = "400", description = "Invalid input"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response createEvent(
            @RequestBody(description = "Event to create", required = true)
            EventDTO eventDTO) {
        try {
            Event event = EventMapper.toEntity(eventDTO);
            eventDAO.persistEvent(event);
            return Response.status(Response.Status.CREATED).entity(eventDTO).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an event", description = "Updates an existing event")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Event updated"),
            @APIResponse(responseCode = "404", description = "Event not found"),
            @APIResponse(responseCode = "400", description = "Invalid input")
    })
    public Response updateEvent(
            @Parameter(name = "id", description = "ID of the event to update", required = true)
            @PathParam("id") Long id,
            @RequestBody(description = "Updated event data", required = true)
            EventDTO eventDTO) {
        Event existingEvent = eventDAO.findById(id);
        if (existingEvent == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Event not found").build();
        }
        Event updatedEvent = EventMapper.toEntity(eventDTO);
        updatedEvent.id = id;
        eventDAO.updateEventName(updatedEvent);
        return Response.ok(EventMapper.toDTO(updatedEvent)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete an event", description = "Deletes an event by ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "Event deleted"),
            @APIResponse(responseCode = "404", description = "Event not found")
    })
    public Response deleteEventById(
            @Parameter(name = "id", description = "ID of the event to delete", required = true)
            @PathParam("id") Long id) throws EventCustomException {
        Event event = eventDAO.findById(id);
        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Event not found").build();
        }
        try {
            eventDAO.deleteEvent(event);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EventCustomException e) {
            throw new EventCustomException("Failed to delete event", e);
        }
    }
}
