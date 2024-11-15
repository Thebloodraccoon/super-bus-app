package ua.bus.app.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.RegisterDTO;
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
        User save = userJpaRepo.save(user);

        return userMapper.toUserDTO(save);
    }

    @Override
    public UserDTO getUserById(Long id) throws UserNotFoundException {
        User user = userJpaRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return userJpaRepo.findAll().stream()
                .map(userMapper::toUserDTO)
                .toList();
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO userDTO) throws UserNotFoundException {
        User user = userJpaRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());

        User updatedUser = userJpaRepo.save(user);
        return userMapper.toUserDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) throws UserNotFoundException {
        if (!userJpaRepo.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userJpaRepo.deleteById(id);
    }
}