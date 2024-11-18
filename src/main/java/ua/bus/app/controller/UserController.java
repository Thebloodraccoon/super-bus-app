package ua.bus.app.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.*;
import ua.bus.app.service.RouteService;
import ua.bus.app.service.TicketService;
import ua.bus.app.service.UserService;
import org.springframework.ui.Model;

import java.util.List;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RouteService routeService;
    private final TicketService ticketService;

    @GetMapping("/client/{id}")
    public String showClientProfile(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        UserDTO userById = userService.getUserById(id);
        model.addAttribute("user", userById);

        List<TicketDTO> tickets = ticketService.findByUserId(id);
        model.addAttribute("tickets", tickets);
        return "client-profile";
    }

    @GetMapping("/client/{id}/routes")
    public String showRoutesForClient(@PathVariable("id") Long id,
                                      @RequestParam(name = "from", required = false) String from,
                                      @RequestParam(name = "to", required = false) String to,
                                    Model model) throws UserNotFoundException {
        UserDTO userById = userService.getUserById(id);
        model.addAttribute("user", userById);

        Pageable pageable = PageRequest.of(0, 20);
        List<RouteDTO> routes = routeService.findRoutesLocation(from, to, pageable);

        model.addAttribute("routes", routes);

        List<TicketDTO> tickets = ticketService.findByUserId(id);
        model.addAttribute("tickets", tickets);

        return "client-profile";
    }

    @GetMapping("/partner/{id}")
    public String showPartnerProfile(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        UserDTO userById = userService.getUserById(id);
        model.addAttribute("user", userById);

        return "partner-profile";
    }

    @PostMapping("/update/{id}")
    public String userUpdate(@PathVariable("id") Long id,
            @RequestParam String editName,
            @RequestParam String editEmail,
            @RequestParam String editPhone) throws UserNotFoundException {

        UserDTO userDTO = new UserDTO();
        userDTO.setName(editName);
        userDTO.setEmail(editEmail);
        userDTO.setPhone(editPhone);

        UserDTO updateUser = userService.updateUser(id, userDTO);

        if (updateUser.getRole().toString().equals("PARTNER")) {
            return "redirect:/user/partner/" + updateUser.getId();
        }

        return "redirect:/user/client/" + updateUser.getId();
    }

    @PostMapping("/delete/{id}")
    public String userDelete(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        userService.deleteUser(id);

        Pageable pageable = PageRequest.of(0, 6);
        List<RouteItemDTO> routes = routeService.getRoutes(pageable);

        model.addAttribute("routes", routes);
        return "index";
    }
}
