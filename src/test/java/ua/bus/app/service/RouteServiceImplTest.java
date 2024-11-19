package ua.bus.app.service;

import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.RouteDTO;
import ua.bus.app.model.dto.RouteItemDTO;
import ua.bus.app.model.dto.StopDTO;
import ua.bus.app.model.dto.UserDTO;
import ua.bus.app.model.entity.Route;
import ua.bus.app.repo.RouteJpaRepo;
import ua.bus.app.repo.UserJpaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class RouteServiceImplTest extends ServiceTestParent {
    @Mock
    private UserJpaRepo userJpaRepoMock;
    @Mock
    private RouteJpaRepo routeJpaRepo;

    private RouteService routeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        routeService = new RouteServiceImpl(routeJpaRepo, routeMapper, userJpaRepoMock);
    }

    @Test
    void createRoute_Success() {
        Long partnerId = 5L;

        RouteDTO route = getRouteDTO();

        when(userJpaRepoMock.findById(partnerId)).thenReturn(Optional.ofNullable(testRoutes.get(0).getPartner()));
        when(routeJpaRepo.save(any())).thenReturn(testRoutes.get(0));

        RouteDTO actualRouteDTO = routeService.createRoute(route, partnerId);

        assertNotNull(actualRouteDTO);
        assertEquals(route.getId(), actualRouteDTO.getId());
        assertEquals(route.getStartLocation(), actualRouteDTO.getStartLocation());
        assertEquals(route.getEndLocation(), actualRouteDTO.getEndLocation());
        assertEquals(route.getDescription(), actualRouteDTO.getDescription());
        assertEquals(route.getPrice(), actualRouteDTO.getPrice());
        assertEquals(route.getStops().size(), actualRouteDTO.getStops().size());
    }

    @Test
    void createRoute_InvalidPartner() {
        Long partnerId = 5L;

        RouteDTO route = getRouteDTO();
        when(userJpaRepoMock.findById(partnerId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> routeService.createRoute(route, partnerId));
        Mockito.verify(userJpaRepoMock).findById(partnerId);
    }

    @Test
    void deleteRoute_Success() {
        Long routeId = 1L;
        when(routeJpaRepo.existsById(routeId)).thenReturn(true);

        routeService.deleteRoute(routeId);

        verify(routeJpaRepo, times(1)).existsById(routeId);
        verify(routeJpaRepo, times(1)).deleteById(routeId);
    }

    @Test
    void deleteRoute_EntityNotFoundException() {
        Long routeId = 1L;

        when(routeJpaRepo.existsById(routeId)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> routeService.deleteRoute(routeId));
    }

    @Test
    void testFindByPartner() throws UserNotFoundException {
        Long partnerId = 5L;

        RouteDTO route = getRouteDTO();

        when(userJpaRepoMock.findById(partnerId)).thenReturn(Optional.ofNullable(testRoutes.get(0).getPartner()));
        when(routeJpaRepo.findByPartner(any())).thenReturn(testRoutes);

        List<RouteDTO> byPartner = routeService.findByPartner(partnerId);
        RouteDTO routeDTO = byPartner.get(0);

        assertNotNull(routeDTO);
        assertEquals(route.getId(), routeDTO.getId());
        assertEquals(route.getStartLocation(), routeDTO.getStartLocation());
        assertEquals(route.getEndLocation(), routeDTO.getEndLocation());
        assertEquals(route.getDescription(), routeDTO.getDescription());
        assertEquals(route.getPrice(), routeDTO.getPrice());
        assertEquals(route.getStops().size(), routeDTO.getStops().size());
    }

    @Test
    void testGetRoutes() {
        Pageable pageable = PageRequest.of(0, 10);

        List<RouteItemDTO> list = testRoutes.stream()
                .map(routeMapper::toRouteItemDTO)
                .collect(Collectors.toList());

        Page<Route> page = new PageImpl<>(testRoutes, pageable, testRoutes.size());

        when(routeJpaRepo.findAll(pageable)).thenReturn(page);

        List<RouteItemDTO> result = routeService.getRoutes(pageable);
        RouteItemDTO routeDTO = result.get(0);

        assertNotNull(routeDTO);
        assertEquals(testRoutes.get(0).getId(), routeDTO.getId());
        assertEquals(testRoutes.get(0).getStartLocation(), routeDTO.getStartLocation());
        assertEquals(testRoutes.get(0).getEndLocation(), routeDTO.getEndLocation());
        assertEquals(testRoutes.get(0).getDescription(), routeDTO.getDescription());
        assertEquals(testRoutes.get(0).getPrice(), routeDTO.getPrice());
    }

    private static @NotNull RouteDTO getRouteDTO() {
        UserDTO partner = new UserDTO();
        partner.setId(5L);
        partner.setName("John Doe");
        partner.setEmail("john.doe@example.com");

        List<StopDTO> stops = new ArrayList<>();

        StopDTO stop1 = new StopDTO();
        stop1.setId(101L);
        stop1.setLocationName("Zhytomyr");
        stops.add(stop1);

        StopDTO stop2 = new StopDTO();
        stop2.setId(102L);
        stop2.setLocationName("Ternopil");
        stops.add(stop2);

        RouteDTO route = new RouteDTO();
        route.setId(1L);
        route.setStartLocation("Kyiv");
        route.setEndLocation("Lviv");
        route.setDescription("Scenic route through the Carpathian Mountains");
        route.setPrice(1000L);
        route.setPartner(partner);
        route.setStops(stops);
        return route;
    }


}