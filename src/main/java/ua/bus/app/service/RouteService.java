package ua.bus.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.bus.app.model.entity.Route;

public interface RouteService {
    Route createRoute(Route route);
    Route getRouteById(Long id);
    Route updateRoute(Long id, Route route);
    void deleteRoute(Long id);

    Page<Route> getRoutes(Pageable pageable);
    Page<Route> findRoutesByStartLocation(String startLocation, Pageable pageable);
}
