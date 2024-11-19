package ua.bus.app.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ua.bus.app.model.entity.Route;
import ua.bus.app.model.entity.User;

import java.util.List;


public interface RouteJpaRepo extends JpaRepository<Route, Long> {
    Page<Route> findByStartLocationAndEndLocation(String startLocation, String endLocation, Pageable pageable);
    Page<Route> findByEndLocation(String endLocation, Pageable pageable);
    Page<Route> findByStartLocation(String startLocation, Pageable pageable);

    List<Route> findByPartner(User partner);

}
