package ua.bus.app.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.bus.app.model.entity.Route;
import ua.bus.app.repo.RouteJpaRepo;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteJpaRepo routeRepo;


    @Override
    public Route getRouteById(Long id) {
        return routeRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Route not found"));
    }

    @Override
    public Route createRoute(Route route) {
        return routeRepo.save(route);
    }

    @Override
    public Route updateRoute(Long id, Route route) {
        if (!routeRepo.existsById(id)) {
            throw new RuntimeException("Route not found");
        }
        route.setId(id);
        return routeRepo.save(route);
    }

    @Override
    public void deleteRoute(Long id) {
        if (!routeRepo.existsById(id)) {
            throw new RuntimeException("Route not found");
        }
        routeRepo.deleteById(id);
    }

    @Override
    public Page<Route> getRoutes(Pageable pageable) {
        Page<Route> all = routeRepo.findAll(pageable);
        System.out.print(all);
        return all;
    }

    @Override
    public Page<Route> findRoutesByStartLocation(String startLocation, Pageable pageable) {
        return routeRepo.findByStartLocationContaining(startLocation, pageable);
    }
}
