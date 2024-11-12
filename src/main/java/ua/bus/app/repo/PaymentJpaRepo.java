package ua.bus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bus.app.model.entity.Payment;

public interface PaymentJpaRepo extends JpaRepository<Payment, Long> {
}
