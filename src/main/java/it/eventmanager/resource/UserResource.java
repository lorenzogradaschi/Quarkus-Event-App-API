package it.eventmanager.resource;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.resteasy.runtime.QuarkusRestPathTemplateInterceptor;
import it.eventmanager.dao.UserDAO;
import it.eventmanager.dto.UserDTO;
import it.eventmanager.entities.Event;
import it.eventmanager.entities.User;
import it.eventmanager.exception.EventCustomException;
import it.eventmanager.exception.UserCustomException;
import it.eventmanager.utilmapper.EventMapper;
import it.eventmanager.utilmapper.UserMapper;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;


@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource extends PanacheEntityBase {

    @Inject
    UserDAO userDAO;

    @GET
    @Operation(summary = "Get all users", description = "Returns a list of all users")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getUsers() {
        List<User> users = userDAO.findAll();
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/by-email")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get User By Email", description = "Return user by email filter")
    public List<UserDTO> getUsersByEmail(
            @Parameter(name = "email", description = "Email to search for", required = true)
            @QueryParam("email") String email
    ) throws UserCustomException {
        try {
            List<User> users = userDAO.findByEmail(email);
            return users.stream()
                    .map(UserMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new UserCustomException("User not found", e);
        }
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new user", description = "Creates a new user")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Created user"),
            @APIResponse(responseCode = "400", description = "Bad Request"),
            @APIResponse(responseCode = "500", description = "Internal Server Error")
    })
    public Response createUser(UserDTO userDTO) {
        User user = UserMapper.toEntity(userDTO);
        try {
            userDAO.persistUser(user);
            return Response.ok(userDTO).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Transactional
    @Operation(summary = "Delete a user", description = "Delete a user by ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "User deleted"),
            @APIResponse(responseCode = "404", description = "User not found")
    })
    public Response deleteUser(
            @RequestBody(description = "User object to create", required = true)
            @Parameter(name = "id", description = "ID of the user to delete", required = true)
            @QueryParam("id") String id
    ) {
        try {
            User user = User.findById(id);
            userDAO.deleteUser(user);
            return Response.status(Response.Status.OK).entity("User Deleted").build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get User by ID", description = "Returns a single User by its ID")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "User found"),
            @APIResponse(responseCode = "404", description = "User not found")
    })
    public Response getUserById(
            @Parameter(name = "id", description = "ID of the User", required = true)
            @PathParam("id") Long id) {
        try{
            User user = userDAO.findById(id);
            return Response.ok(UserMapper.toDTO(user)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/most-active")
    @Transactional
    @Operation(summary = "Get User with most Attended events" , description = "return the user with the most attended events")
    public Response getUserWithMostAttendedEvents() throws UserCustomException {
        try{
            User user = new User();
            //TODO with criteria query the method findUserWithMostAttendedEvents();
            //User user = UserDAO.findUserWithMostAttendedEvents();
            return Response.ok(UserMapper.toDTO(user)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}
