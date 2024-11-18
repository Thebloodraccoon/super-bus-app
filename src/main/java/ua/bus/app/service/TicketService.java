package ua.bus.app.service;

import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.TicketDTO;

import java.util.List;

public interface TicketService {
    TicketDTO save(TicketDTO ticketDTO, Long userId, Long ticketId) throws UserNotFoundException;

    List<TicketDTO> findByUserId(Long userId);
}
