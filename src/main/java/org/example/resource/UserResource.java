package org.example.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.time.LocalDate;
import java.util.stream.Collectors;

import org.example.model.User;
import org.example.dto.UserDTO;
import org.example.mapper.UserMapper;

import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.Authenticated;
import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;

/**
 * The UserResource class handles HTTP requests related to User operations.
 * - It provides endpoints for fetching, creating, and deleting users.
 * - It uses DTOs to decouple the internal model from API contracts.
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Authenticated 
public class UserResource {

    @Inject
    JsonWebToken jwt; 

    /**
     * GET endpoint to fetch all users.
     * Converts User entities to UserDTOs before returning the response.
     * Restricted to logged-in users.
     * 
     * @return A list of UserDTOs.
     */
    @GET
    @RolesAllowed({"Admin", "User"}) 
    public List<UserDTO> getAllUsers() {
        return User.<User>listAll().stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * GET endpoint to fetch one user, passed the specific id.
     * Restricted to logged-in users.
     * 
     * @param id The ID of the user.
     * @return A response with the user's details or an error if not found.
     */
    @GET
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"Admin", "User"}) 
    public Response getUserById(@PathParam("id") Long id) {
        User user = User.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found"))
                    .build();
        }
        return Response.status(Response.Status.OK).entity(UserMapper.toDTO(user)).build();
    }

    /**
     * POST endpoint to create a new user.
     * This endpoint is open and does not require authentication.
     * 
     * @param userDTO The UserDTO from the request body.
     * @return A response with the created UserDTO.
     */
    @POST
    @Transactional
    @PermitAll
    public Response createUser(UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Email is required"))
                    .build();
        }
        LocalDate date = LocalDate.now();
        User user = UserMapper.toEntity(userDTO);
        user.setPassword(BcryptUtil.bcryptHash(userDTO.getPassword())); // Hash password here
        user.setDate(date);
        user.persist();
        return Response.status(Response.Status.CREATED).entity(UserMapper.toDTO(user)).build();
    }


    /**
     * DELETE endpoint to delete a user by ID.
     * Restricted to logged-in users.
     * 
     * @param id The ID of the user to delete.
     * @return A 204 No Content response if successful, or 404 Not Found if the user doesn't exist.
     */
    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed({"Admin"}) 
    public Response deleteUser(@PathParam("id") Long id) {
        boolean deleted = User.deleteById(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("User not found"))
                    .build();
        }
    }

    /**
     * ErrorResponse for sending error details in API responses.
     */
    public static class ErrorResponse {
        public String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
