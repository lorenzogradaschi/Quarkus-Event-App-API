package org.example.dto;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * The UserDTO (Data Transfer Object) is a plain Java object designed to:
 * - Decouple the internal database model (User entity) from the API response.
 * - Protect sensitive fields (like internal IDs or passwords).
 * - Customize the API's data structure to fit client requirements.
 */
public class UserDTO extends PanacheEntity{

    private String name;

    private String surname;

    private String email;
    
    private String password;
    
    private String role;
    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}
