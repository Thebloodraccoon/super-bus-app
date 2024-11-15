package ua.bus.app.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import ua.bus.app.model.dto.RouteDTO;
import ua.bus.app.model.dto.RouteItemDTO;
import ua.bus.app.model.entity.Route;
import ua.bus.app.service.AuthService;
import ua.bus.app.service.RouteService;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final RouteService routeService;
    private final AuthService authService;

    @GetMapping
    public String home(@NotNull Model model) {
        Pageable pageable = PageRequest.of(0, 6);
        List<RouteItemDTO> routes = routeService.getRoutes(pageable);

        model.addAttribute("routes", routes);

        return "index";
    }
}
