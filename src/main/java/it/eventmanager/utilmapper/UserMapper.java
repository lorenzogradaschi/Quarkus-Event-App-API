package it.eventmanager.utilmapper;

import it.eventmanager.dto.UserDTO;
import it.eventmanager.entities.User;

public class UserMapper {

    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.name);
        user.setSurname(userDTO.surname);
        user.setEmail(userDTO.email);
        user.setPassword(userDTO.password);
        user.setDateOfBirth(userDTO.dateOfBirth);
        user.setGender(userDTO.gender);
        user.setAddress(userDTO.address);
        return user;
    }

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.id = user.id;
        userDTO.name = user.getName();
        userDTO.surname = user.getSurname();
        userDTO.email = user.getEmail();
        userDTO.password = user.getPassword();
        userDTO.dateOfBirth = user.getDateOfBirth();
        userDTO.gender = user.getGender();
        userDTO.address = user.getAddress();
        return userDTO;
    }
}
