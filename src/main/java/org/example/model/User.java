package org.example.model;

import jakarta.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.time.LocalDate; // import the LocalDate class

/**
 * The User entity represents the database table for storing user information.
 * It extends PanacheEntity to provide built-in methods for CRUD operations.
 */
@Entity
public class User extends PanacheEntity {

    public String name;

    public String surname;

    public String email;
    
    public String password;

    public LocalDate date;
    
    public String role;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
