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

    public User loginUser(LoginDTO loginDTO) throws PasswordEncryptionException {
        try {
            User user = userJpaRepo.findByEmail(loginDTO.getEmail());

            if (user == null) throw new UserNotFoundException("Invalid email");

            String decryptedPassword = user.getPasswordHash();

            if (!decryptedPassword.equals(loginDTO.getPassword())) {
                throw new InvalidPasswordException("Invalid password");
            }

            return user;
        } catch (Exception e) {
            throw new PasswordEncryptionException("Error decrypting password");
        }
    }

    public User registerUser(RegisterDTO registerDTO) throws UserAlreadyExistsException {
            User existed = userJpaRepo.findByEmail(registerDTO.getEmail());

            if (existed != null) throw new UserAlreadyExistsException("User already exists");

            User user = new User();
            user.setEmail(registerDTO.getEmail());
            user.setName(registerDTO.getName());
            user.setPhone(registerDTO.getPhone());

            Role role = Role.fromString(registerDTO.getRole());
            user.setRole(role);

            String encryptedPassword = registerDTO.getPassword();
            user.setPasswordHash(registerDTO.getPassword());

            User saved = userJpaRepo.save(user);

            return saved;
    }
}