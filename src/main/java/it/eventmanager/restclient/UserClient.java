package it.eventmanager.restclient;

import it.eventmanager.dto.UserDTO;
import it.eventmanager.entities.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Path("/users")
@RegisterRestClient(configKey = "extensions-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface UserClient {

    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getUsers();

    @GET
    @Path("/by-email")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDTO> getUsersByEmail(@QueryParam("email") String email) throws SQLException, UsernameNotFoundException;

    @POST
    @Transactional
    public Response createUser(UserDTO userDTO);

    @DELETE
    @Transactional
    public Response deleteUser(@QueryParam("id") String id);

    @GET
    @Path("/most-active")
    @Transactional
    public User getUserWithMostAttendedEvents();

}
