package ua.bus.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.bus.app.model.dto.RouteDTO;
import ua.bus.app.service.RouteService;

import java.util.List;

@Controller
@RequestMapping("/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping
    public String routes(
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            Model model) {
        Pageable pageable = PageRequest.of(0, 20);
        List<RouteDTO> routes = routeService.findRoutesLocation(from, to, pageable);

        model.addAttribute("routes", routes);
        return "routes";
    }
}
