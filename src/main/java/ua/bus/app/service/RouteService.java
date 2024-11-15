package ua.bus.app.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.bus.app.model.dto.RouteDTO;
import ua.bus.app.model.dto.RouteItemDTO;

import java.util.List;

public interface RouteService {
    RouteDTO createRoute(RouteDTO route);
    RouteDTO getRouteById(Long id);
    RouteDTO updateRoute(Long id, RouteDTO route);
    void deleteRoute(Long id);

    List<RouteItemDTO> getAllRoutes();
    List<RouteItemDTO> getRoutes(Pageable pageable);
    Page<RouteDTO> findRoutesByStartLocation(String startLocation, Pageable pageable);
}
