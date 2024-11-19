package ua.bus.app.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.UserDTO;
import ua.bus.app.model.entity.User;
import ua.bus.app.model.mapper.UserMapper;
import ua.bus.app.repo.UserJpaRepo;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserJpaRepo userJpaRepo;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toUser(userDTO);
        return saveAndMapToDTO(user);
    }

    @Override
    public UserDTO getUserById(Long id) throws UserNotFoundException {
        User user = findUserById(id); // Extract Method
        return userMapper.toUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userJpaRepo.findAll().stream()
                .map(userMapper::toUserDTO)
                .toList(); // Simplified stream
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) throws UserNotFoundException {
        User user = findUserById(id); // Extract Method
        updateUserFields(user, userDTO); // Extract Method
        return saveAndMapToDTO(user);
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        validateUserExistence(id); // Extract Method
        userJpaRepo.deleteById(id);
    }

    // --- Приватні методи для рефакторингу ---
    private User findUserById(Long id) throws UserNotFoundException {
        return userJpaRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    private void validateUserExistence(Long id) throws UserNotFoundException {
        if (!userJpaRepo.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
    }

    private void updateUserFields(User user, UserDTO userDTO) {
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
    }

    private UserDTO saveAndMapToDTO(User user) {
        User savedUser = userJpaRepo.save(user);
        return userMapper.toUserDTO(savedUser);
    }
}
