package ua.bus.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.bus.app.model.entity.Route;


public interface RouteJpaRepo extends JpaRepository<Route, Long> {
    Page<Route> findByStartLocationContaining(String startLocation, Pageable pageable);
}
