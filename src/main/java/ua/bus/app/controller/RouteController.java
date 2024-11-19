package ua.bus.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.bus.app.model.dto.RouteDTO;
import ua.bus.app.model.dto.StopDTO;
import ua.bus.app.service.RouteService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping("/routes")
    public String routes(
            @RequestParam(name = "from", required = false) String from,
            @RequestParam(name = "to", required = false) String to,
            Model model) {
        Pageable pageable = PageRequest.of(0, 20);
        List<RouteDTO> routes = routeService.findRoutesLocation(from, to, pageable);

        model.addAttribute("routes", routes);
        return "routes";
    }

    @PostMapping("user/create/{id}")
    public String routeCreate(
            @PathVariable("id") Long id,
            @RequestParam(name = "startLocation") String startLocation,
            @RequestParam(name = "endLocation") String endLocation,
            @RequestParam(name = "description") String description,
            @RequestParam(name = "price") String price,
            Model model) {


        RouteDTO routeDTO = new RouteDTO();
        routeDTO.setStartLocation(startLocation);
        routeDTO.setEndLocation(endLocation);
        routeDTO.setDescription(description);
        routeDTO.setPrice(Long.parseLong(price));
        routeDTO.setStops(new ArrayList<>());

        RouteDTO route = routeService.createRoute(routeDTO, id);

        return "redirect:/user/partner/" + id;
    }

    @PostMapping("user/delete/route/{id}")
    public String routeCreate(
            @PathVariable("id") Long id,
            @RequestParam(name = "userId") String userId,
            Model model) {

        routeService.deleteRoute(id);

        return "redirect:/user/partner/" + userId;
    }
}
