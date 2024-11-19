package ua.bus.app.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.RouteDTO;
import ua.bus.app.model.dto.RouteItemDTO;
import ua.bus.app.model.dto.UserDTO;
import ua.bus.app.model.entity.Route;
import ua.bus.app.model.entity.User;
import ua.bus.app.model.mapper.RouteMapper;
import ua.bus.app.repo.RouteJpaRepo;
import ua.bus.app.repo.UserJpaRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteJpaRepo routeRepo;
    private final RouteMapper routeMapper;
    private final UserJpaRepo userJpaRepo;

    @Override
    public RouteDTO getRouteById(Long id) {
        Route route = routeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route with ID " + id + " not found"));
        return routeMapper.toRouteDTO(route);
    }

    @Override
    public RouteDTO createRoute(RouteDTO routeDTO, Long partnerId) {
        User partner = userJpaRepo.findById(partnerId).get();


        Route route = routeMapper.toRoute(routeDTO);
        route.setPartner(partner);
        Route savedRoute = routeRepo.save(route);
        return routeMapper.toRouteDTO(savedRoute);
    }

    @Override
    public RouteDTO updateRoute(Long id, RouteDTO routeDTO) {
        if (!routeRepo.existsById(id)) {
            throw new EntityNotFoundException("Route with ID " + id + " not found");
        }
        Route route = routeMapper.toRoute(routeDTO);
        route.setId(id);
        Route updatedRoute = routeRepo.save(route);
        return routeMapper.toRouteDTO(updatedRoute);
    }

    @Override
    public void deleteRoute(Long id) {
        if (!routeRepo.existsById(id)) {
            throw new EntityNotFoundException("Route with ID " + id + " not found");
        }
        routeRepo.deleteById(id);
    }

    @Override
    public List<RouteDTO> findByPartner(Long partnerId) throws UserNotFoundException {
        User partner = userJpaRepo.findById(partnerId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        List<Route> routes = routeRepo.findByPartner(partner);
        return routes.stream()
                .map(routeMapper::toRouteDTO)
                .toList();
    }

    @Override
    public List<RouteItemDTO> getAllRoutes() {
        List<Route> routes = routeRepo.findAll();
        return routes.stream()
                .map(routeMapper::toRouteItemDTO)
                .toList();
    }

    @Override
    public List<RouteItemDTO> getRoutes(Pageable pageable) {
        List<Route> routes = routeRepo.findAll(pageable).getContent();
        return routes.stream()
                .map(routeMapper::toRouteItemDTO)
                .toList();
    }

    @Override
    public List<RouteDTO> findRoutesLocation(String startLocation, String endLocation, Pageable pageable) {
        if ((startLocation == null || startLocation.isEmpty()) && (endLocation == null || endLocation.isEmpty())) {
            return routeRepo.findAll(pageable).stream()
                    .map(routeMapper::toRouteDTO)
                    .collect(Collectors.toList());
        }
        if (startLocation != null && !startLocation.isEmpty() && endLocation != null && !endLocation.isEmpty()) {
            return routeRepo.findByStartLocationAndEndLocation(startLocation, endLocation, pageable).stream()
                    .map(routeMapper::toRouteDTO)
                    .collect(Collectors.toList());
        }

        if (endLocation != null && !endLocation.isEmpty()) {
            return routeRepo.findByEndLocation(endLocation, pageable).stream()
                    .map(routeMapper::toRouteDTO)
                    .collect(Collectors.toList());
        }

        return routeRepo.findByStartLocation(startLocation, pageable).stream()
                .map(routeMapper::toRouteDTO)
                .collect(Collectors.toList());
    }
}
