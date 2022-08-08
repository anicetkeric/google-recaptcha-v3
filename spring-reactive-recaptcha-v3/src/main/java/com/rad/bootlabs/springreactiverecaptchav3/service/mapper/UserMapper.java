package com.rad.bootlabs.springreactiverecaptchav3.service.mapper;

import com.rad.bootlabs.springreactiverecaptchav3.model.User;
import com.rad.bootlabs.springreactiverecaptchav3.service.mapper.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream().filter(Objects::nonNull).map(this::userToUserDTO).collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(User user) {
        if (user == null) {
            return null;
        } else {
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(userDTO.getUsername());
            userDTO.setFirstName(userDTO.getFirstName());
            userDTO.setLastName(userDTO.getLastName());
            userDTO.setEmail(userDTO.getEmail());
            return userDTO;
        }
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            return user;
        }
    }

}
