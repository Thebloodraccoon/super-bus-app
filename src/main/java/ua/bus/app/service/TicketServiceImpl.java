package ua.bus.app.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.TicketDTO;
import ua.bus.app.model.entity.Route;
import ua.bus.app.model.entity.Ticket;
import ua.bus.app.model.entity.User;
import ua.bus.app.model.mapper.TicketMapper;
import ua.bus.app.repo.RouteJpaRepo;
import ua.bus.app.repo.TicketJpaRepo;
import ua.bus.app.repo.UserJpaRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketServiceImpl implements TicketService {
    private final TicketJpaRepo ticketJpaRepo;
    private final TicketMapper ticketMapper;
    private final UserJpaRepo userJpaRepo;
    private final RouteJpaRepo routeJpaRepo;

    @Override
    public TicketDTO save(TicketDTO ticketDTO, Long userId, Long routeId) throws UserNotFoundException {
        User user = findUserById(userId);
        Route route = findRouteById(routeId);

        Ticket ticket = ticketMapper.toTicket(ticketDTO);
        assignRelationsToTicket(ticket, user, route);

        Ticket savedTicket = ticketJpaRepo.save(ticket);
        return ticketMapper.toTicketDTO(savedTicket);
    }

    @Override
    public List<TicketDTO> findByUserId(Long userId) {
        List<Ticket> tickets = ticketJpaRepo.findByUserId(userId);
        return mapTicketsToDTOs(tickets);
    }

    // Extracted methods for modularity and readability
    private User findUserById(Long userId) throws UserNotFoundException {
        return userJpaRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    private Route findRouteById(Long routeId) {
        return routeJpaRepo.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route with ID " + routeId + " not found"));
    }

    private void assignRelationsToTicket(Ticket ticket, User user, Route route) {
        ticket.setUser(user);
        ticket.setRoute(route);
    }

    private List<TicketDTO> mapTicketsToDTOs(List<Ticket> tickets) {
        return tickets.stream()
                .map(ticketMapper::toTicketDTO)
                .toList();
    }
}
