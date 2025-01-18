package org.example.dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Request payload for user login")
public class LoginRequestDTO {

    @Schema(description = "User's email address", example = "user@example.com", required = true)
    private String email;

    @Schema(description = "User's password", example = "securePassword123", required = true)
    private String password;

    @Schema(description = "User's role (optional)", example = "User", required = false)
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
