package ua.bus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bus.app.model.entity.Ticket;

import java.util.List;

public interface TicketJpaRepo extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUserId(Long userId);
}
