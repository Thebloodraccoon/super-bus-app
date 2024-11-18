package ua.bus.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.RouteDTO;
import ua.bus.app.model.dto.TicketDTO;
import ua.bus.app.model.dto.UserDTO;
import ua.bus.app.service.RouteService;
import ua.bus.app.service.TicketService;
import ua.bus.app.service.UserService;

import java.time.LocalDate;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class TicketController {
    private final TicketService ticketService;

    @PostMapping("/client/{id}")
    public String buyTicket(
            @PathVariable("id") Long userById,
            @RequestParam("routeId") Long routeId,
            @RequestParam("purchaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate purchaseDate,
            @RequestParam("price") Double price,
            @RequestParam("seats") Integer seats,
            Model model) throws UserNotFoundException {

        TicketDTO ticket = new TicketDTO();
        ticket.setSeats(seats);
        ticket.setStatus("куплений");
        ticket.setPurchaseDate(purchaseDate.atStartOfDay());
        ticket.setPrice(price);

        TicketDTO save = ticketService.save(ticket, userById, routeId);

        return "redirect:/user/client/" + userById;
    }

}
