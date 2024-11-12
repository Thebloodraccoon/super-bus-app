package ua.bus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bus.app.model.entity.User;

public interface UserJpaRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
