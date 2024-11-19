package ua.bus.app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.bus.app.exception.InvalidPasswordException;
import ua.bus.app.exception.PasswordEncryptionException;
import ua.bus.app.exception.UserAlreadyExistsException;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.LoginDTO;
import ua.bus.app.model.dto.RegisterDTO;
import ua.bus.app.model.entity.User;
import ua.bus.app.model.entity.enums.Role;
import ua.bus.app.repo.UserJpaRepo;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserJpaRepo userJpaRepo;

    public User loginUser(LoginDTO loginDTO) throws PasswordEncryptionException, InvalidPasswordException, UserNotFoundException {
        try {
            User user = findUserByEmail(loginDTO.getEmail()); // Extract Method

            validatePassword(loginDTO.getPassword(), user.getPasswordHash()); // Extract Method

            return user;
        } catch (InvalidPasswordException | UserNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new PasswordEncryptionException("Error decrypting password", e); // Покращено виняток
        }
    }

    public User registerUser(RegisterDTO registerDTO) throws UserAlreadyExistsException {
        if (isUserExists(registerDTO.getEmail())) { // Replace Temp with Query
            throw new UserAlreadyExistsException("User already exists");
        }

        User user = mapRegisterDTOToUser(registerDTO); // Extract Method
        return userJpaRepo.save(user);
    }


    private User findUserByEmail(String email) throws UserNotFoundException {
        return userJpaRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Invalid email"));
    }

    private void validatePassword(String inputPassword, String storedPassword) throws InvalidPasswordException {
        if (!storedPassword.equals(inputPassword)) {
            throw new InvalidPasswordException("Invalid password");
        }
    }

    private boolean isUserExists(String email) {
        return userJpaRepo.findByEmail(email).isPresent();
    }

    private User mapRegisterDTOToUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setName(registerDTO.getName());
        user.setPhone(registerDTO.getPhone());
        user.setRole(Role.fromString(registerDTO.getRole()));
        user.setPasswordHash(registerDTO.getPassword()); // Encrypt if needed
        return user;
    }
}

