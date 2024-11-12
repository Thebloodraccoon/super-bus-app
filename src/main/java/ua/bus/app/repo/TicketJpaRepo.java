package ua.bus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bus.app.model.entity.Ticket;

public interface TicketJpaRepo extends JpaRepository<Ticket, Long> {
}
