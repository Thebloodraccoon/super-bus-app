package ua.bus.app.service;

import org.springframework.data.domain.Pageable;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.RouteDTO;
import ua.bus.app.model.dto.RouteItemDTO;
import ua.bus.app.model.dto.UserDTO;
import ua.bus.app.model.entity.Route;

import java.util.List;

public interface RouteService {
    RouteDTO createRoute(RouteDTO route, Long partnerId);
    RouteDTO getRouteById(Long id);
    RouteDTO updateRoute(Long id, RouteDTO route);
    void deleteRoute(Long id);

    List<RouteDTO> findByPartner(Long partnerId) throws UserNotFoundException;


    List<RouteItemDTO> getAllRoutes();
    List<RouteItemDTO> getRoutes(Pageable pageable);
    List<RouteDTO> findRoutesLocation(String startLocation, String endLocation, Pageable pageable);
}
