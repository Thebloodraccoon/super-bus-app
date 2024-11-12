package ua.bus.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.bus.app.model.entity.Schedule;

public interface ScheduleJpaRepo extends JpaRepository<Schedule, Long> {
}
