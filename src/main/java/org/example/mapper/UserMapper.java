package org.example.mapper;

import org.example.dto.UserDTO;
import org.example.model.User;
import jakarta.enterprise.context.ApplicationScoped;


/**
 * The UserMapper class is responsible for converting between:
 * - User entity (internal database representation).
 * - UserDTO (external API data structure).
 * 
 * 
 * 
	 * In order to be able to map a model into
	 * a DTO you need always to create a 
	 * public static method that gets the real 
	 * model and grabs the dto, and set the 
	 * properties of the dto of the model
	 * 
	 * use the getters and setters of default
	 * of the class generated
	 * User user = new User();  then dto.setName(user.name);
 */
@ApplicationScoped
public class UserMapper {

    /**
     * Converts a User entity to a UserDTO.
     * 
     * @param user The User entity to map.
     * @return The mapped UserDTO.
     */
    public static UserDTO toDTO(User user) {
        if (user == null) 
            return null; 
        UserDTO dto = new UserDTO();
        dto.setName(user.name);      
        dto.setSurname(user.surname); 
        dto.setEmail(user.email);   
        dto.setPassword(user.password);
        dto.setRole(user.role);
        return dto;
    }

    /**
     * Converts a UserDTO to a User entity.
     * 
     * @param dto The UserDTO to map.
     * @return The mapped User entity.
     */
    public static User toEntity(UserDTO dto) {
        if (dto == null) 
            return null; 
        User user = new User();
        user.name = dto.getName();      
        user.surname = dto.getSurname(); 
        user.email = dto.getEmail();     
        user.password = dto.getPassword();
        user.role = dto.getRole();
        return user;
    }
}
