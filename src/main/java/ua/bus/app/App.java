package ua.bus.app;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.bus.app.model.dto.RouteDTO;
import ua.bus.app.model.dto.TicketDTO;
import ua.bus.app.model.dto.UserDTO;
import ua.bus.app.model.entity.Route;
import ua.bus.app.repo.RouteJpaRepo;
import ua.bus.app.service.RouteService;
import ua.bus.app.service.TicketService;
import ua.bus.app.service.UserService;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
@RequiredArgsConstructor
public class App implements CommandLineRunner {
    private final RouteService routeService;
    private final RouteJpaRepo routeRepo;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        RouteDTO routeById = routeService.getRouteById(1L);
//        System.out.print(routeById);
//
//        Optional<Route> byId = routeRepo.findById(1L);
//        System.out.print(byId.get());

//        List<RouteDTO> byPartner = routeService.findByPartner(3L);
//        System.out.print(byPartner);
    }
}