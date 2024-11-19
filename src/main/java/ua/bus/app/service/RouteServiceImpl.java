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
        return routeMapper.toRouteDTO(getRouteByIdOrThrow(id));
    }

    @Override
    public RouteDTO createRoute(RouteDTO routeDTO, Long partnerId) {
        User partner = getUserByIdOrThrow(partnerId);
        Route route = routeMapper.toRoute(routeDTO);
        route.setPartner(partner);
        return routeMapper.toRouteDTO(routeRepo.save(route));
    }

    @Override
    public RouteDTO updateRoute(Long id, RouteDTO routeDTO) {
        validateRouteExistence(id);
        Route route = routeMapper.toRoute(routeDTO);
        route.setId(id);
        return routeMapper.toRouteDTO(routeRepo.save(route));
    }

    @Override
    public void deleteRoute(Long id) {
        validateRouteExistence(id);
        routeRepo.deleteById(id);
    }

    @Override
    public List<RouteDTO> findByPartner(Long partnerId) throws UserNotFoundException {
        User partner = getUserByIdOrThrow(partnerId);
        return routeRepo.findByPartner(partner).stream()
                .map(routeMapper::toRouteDTO)
                .toList();
    }

    @Override
    public List<RouteItemDTO> getAllRoutes() {
        return routeRepo.findAll().stream()
                .map(routeMapper::toRouteItemDTO)
                .toList();
    }

    @Override
    public List<RouteItemDTO> getRoutes(Pageable pageable) {
        return routeRepo.findAll(pageable).getContent().stream()
                .map(routeMapper::toRouteItemDTO)
                .toList();
    }

    @Override
    public List<RouteDTO> findRoutesLocation(String startLocation, String endLocation, Pageable pageable) {
        if (isNullOrEmpty(startLocation) && isNullOrEmpty(endLocation)) {
            return getAllRoutes(pageable);
        }
        if (!isNullOrEmpty(startLocation) && !isNullOrEmpty(endLocation)) {
            return getRoutesByStartAndEndLocations(startLocation, endLocation, pageable);
        }
        if (!isNullOrEmpty(endLocation)) {
            return getRoutesByEndLocation(endLocation, pageable);
        }
        return getRoutesByStartLocation(startLocation, pageable);
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    private List<RouteDTO> getAllRoutes(Pageable pageable) {
        return routeRepo.findAll(pageable).stream()
                .map(routeMapper::toRouteDTO)
                .collect(Collectors.toList());
    }

    private List<RouteDTO> getRoutesByStartAndEndLocations(String startLocation, String endLocation, Pageable pageable) {
        return routeRepo.findByStartLocationAndEndLocation(startLocation, endLocation, pageable).stream()
                .map(routeMapper::toRouteDTO)
                .collect(Collectors.toList());
    }

    private List<RouteDTO> getRoutesByEndLocation(String endLocation, Pageable pageable) {
        return routeRepo.findByEndLocation(endLocation, pageable).stream()
                .map(routeMapper::toRouteDTO)
                .collect(Collectors.toList());
    }

    private List<RouteDTO> getRoutesByStartLocation(String startLocation, Pageable pageable) {
        return routeRepo.findByStartLocation(startLocation, pageable).stream()
                .map(routeMapper::toRouteDTO)
                .collect(Collectors.toList());
    }

    private Route getRouteByIdOrThrow(Long id) {
        return routeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route with ID " + id + " not found"));
    }

    private User getUserByIdOrThrow(Long id) {
        return userJpaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));
    }

    private void validateRouteExistence(Long id) {
        if (!routeRepo.existsById(id)) {
            throw new EntityNotFoundException("Route with ID " + id + " not found");
        }
    }
}
