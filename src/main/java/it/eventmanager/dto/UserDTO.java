package it.eventmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.smallrye.common.constraint.NotNull;
import it.eventmanager.entities.Address;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long id;

    @NotNull
    public String name;

    @NotNull
    public String surname;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

    @NotNull
    public String email;

    @NotNull
    public String dateOfBirth;

    @NotNull
    public String gender;

    @NotNull
    public Address address;

    @Override
    public String toString() {
        return "UserDTO [name=" + name + ", surname=" + surname + ", email=" + email;
    }
}
