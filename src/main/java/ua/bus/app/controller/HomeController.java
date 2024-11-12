package ua.bus.app.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import ua.bus.app.model.entity.Route;
import ua.bus.app.service.RouteService;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {
    private final RouteService routeService;

    public String home(@NotNull Model model) {
        Pageable pageable = PageRequest.of(0, 2);
        List<Route> routes = routeService.getRoutes(pageable).getContent();

        model.addAttribute("routes", routes);

        return "index";
    }
}
