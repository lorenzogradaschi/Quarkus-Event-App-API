package org.example.resource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

import org.example.dto.EventDTO;
import org.example.mapper.EventMapper;
import org.example.model.Event;
import org.example.model.User;
import org.example.resource.UserResource.ErrorResponse;

import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.inject.Inject;

@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated
public class EventResource {

    @Inject
    JsonWebToken jwt;

    @GET
    public List<EventDTO> getAllEvents() {
        return Event.<Event>listAll().stream()
            .map(EventMapper::toDTO)
            .collect(Collectors.toList());
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin", "User"})
    public Response createEvent(EventDTO eventDTO) {
        Long creatorId = Long.parseLong(jwt.getSubject());
        User creator = User.findById(creatorId);
        if (creator == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        Event event = EventMapper.toEntity(eventDTO, creator, null);
        event.persist();

        return Response.status(Response.Status.CREATED).entity(EventMapper.toDTO(event)).build();
    }

    @POST
    @Path("/{id}/attend")
    @Transactional
    @RolesAllowed({"Admin", "User"})
    public Response attendEvent(@PathParam("id") Long eventId) {
        Long userId = Long.parseLong(jwt.getSubject());
        User user = User.findById(userId);
        Event event = Event.findById(eventId);

        if (event == null || user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        event.getAttendees().add(user);
        event.persist();

        return Response.ok(EventMapper.toDTO(event)).build();
    }
    
    
    @POST
    @Path("/{id}/")
    @Transactional
    @RolesAllowed({"Admin", "User"})
    public Response deleteEvent(@PathParam("id") Long eventId) {
        Long userId = Long.parseLong(jwt.getSubject());
        User user = User.findById(userId);
        Event event = Event.findById(eventId);

        if (event == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("Event not found"))
                    .build();
        }

        if (!user.role.equals("Admin") && (event.creator == null || !event.creator.id.equals(userId))) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorResponse("You are not authorized to delete this event"))
                    .build();
        }

        event.delete();

        return Response.ok(EventMapper.toDTO(event)).build();
    }

}
