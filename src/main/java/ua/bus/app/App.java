package ua.bus.app;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ua.bus.app.model.dto.TicketDTO;
import ua.bus.app.model.dto.UserDTO;
import ua.bus.app.model.entity.Route;
import ua.bus.app.service.RouteService;
import ua.bus.app.service.TicketService;
import ua.bus.app.service.UserService;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class App implements CommandLineRunner {
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserDTO userById = userService.getUserById(1L);

        System.out.print(userById);
    }
}