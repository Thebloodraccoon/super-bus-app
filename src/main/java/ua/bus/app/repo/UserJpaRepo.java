package ua.bus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bus.app.model.entity.User;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
