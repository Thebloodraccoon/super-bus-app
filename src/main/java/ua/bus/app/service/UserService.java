package ua.bus.app.service;

import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.UserDTO;
import ua.bus.app.model.entity.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);
    UserDTO getUserById(Long id) throws UserNotFoundException;
    List<UserDTO> getAllUsers();
    UserDTO updateUser(Long id, UserDTO userDTO) throws UserNotFoundException;
    void deleteUser(Long id) throws UserNotFoundException;
}