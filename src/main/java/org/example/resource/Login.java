package org.example.resource;

import org.example.model.User;
import org.example.dto.LoginRequestDTO;
import org.example.mapper.UserMapper;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.eclipse.microprofile.openapi.annotations.*;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Path("/login")
public class Login {

    @Inject
    EntityManager em;

    @Inject
    UserMapper userMapper;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "User login", description = "Authenticates user and returns a JWT token")
    @APIResponse(responseCode = "200", description = "JWT token",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = TokenResponse.class)))
    @APIResponse(responseCode = "401", description = "Unauthorized access")
    @APIResponse(responseCode = "500", description = "Internal server error")
    public Response login(LoginRequestDTO loginRequest) {
        try {
            User user = findUserByEmailAndRole(loginRequest.getEmail(), loginRequest.getRole());
            if (user == null || !BcryptUtil.matches(loginRequest.getPassword(), user.getPassword())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Invalid email or password"))
                        .build();
            }

            String role = determineUserRole(user.getEmail());
            String token = generateJwtToken(user.getEmail(), role);

            return Response.ok(new TokenResponse(token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("An unexpected error occurred"))
                    .build();
        }
    }

    private User findUserByEmailAndRole(String email, String role) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.role = :role", User.class)
                    .setParameter("email", email)
                    .setParameter("role", role)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private String determineUserRole(String email) {
        if ("lorenzogradaschi@gmail.com".equalsIgnoreCase(email) || "admin@gmail.com".equalsIgnoreCase(email)) {
            return "Admin";
        }
        return "User";
    }

    private String generateJwtToken(String email, String role) {
        Set<String> roles = new HashSet<>(Collections.singletonList(role));
        return Jwt.issuer("http://localhost")
                .upn(email)
                .groups(roles)
                .sign();
    }

    public static class TokenResponse {
        public String token;

        public TokenResponse(String token) {
            this.token = token;
        }
    }

    public static class ErrorResponse {
        public String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
