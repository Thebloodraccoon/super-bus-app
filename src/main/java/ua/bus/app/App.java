package ua.bus.app;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.bus.app.model.entity.Route;
import ua.bus.app.service.RouteService;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class App implements CommandLineRunner {
    private final RouteService routeService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        Pageable pageable = PageRequest.of(0, 6);
//        List<Route> routes = routeService.getRoutes(pageable);
//        System.out.println(routes);
//
//
//        List<Route> allRoutes = routeService.getAllRoutes();
//        System.out.println(allRoutes);
    }
}