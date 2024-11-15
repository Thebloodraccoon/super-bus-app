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
import ua.bus.app.repo.UserJpaRepo;
import ua.bus.app.util.EncryptionUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserJpaRepo userJpaRepo;

    public Long loginUser(LoginDTO loginDTO) throws PasswordEncryptionException {
        try {
            User user = userJpaRepo.findByEmail(loginDTO.getEmail());

            if (user == null) throw new UserNotFoundException("Invalid email");

            String decryptedPassword = EncryptionUtil.decrypt(user.getPasswordHash());

            if (!decryptedPassword.equals(loginDTO.getPassword())) {
                throw new InvalidPasswordException("Invalid password");
            }

            return user.getId();
        } catch (Exception e) {
            throw new PasswordEncryptionException("Error decrypting password");
        }
    }

    public Long registerUser(RegisterDTO registerDTO) throws PasswordEncryptionException {
        try {
            User existed = userJpaRepo.findByEmail(registerDTO.getEmail());

            if (existed != null) throw new UserAlreadyExistsException("User already exists");

            User user = new User();
            user.setEmail(registerDTO.getEmail());
            user.setName(registerDTO.getName());
            user.setPhone(registerDTO.getPhone());
            user.setRole(User.Role.valueOf(registerDTO.getRole()));

            // Encrypt password before saving
            String encryptedPassword = EncryptionUtil.encrypt(registerDTO.getPassword());
            user.setPasswordHash(encryptedPassword);

            User saved = userJpaRepo.save(user);

            return saved.getId();
        } catch (Exception e) {
            throw new PasswordEncryptionException("Error encrypting password");
        }
    }
}