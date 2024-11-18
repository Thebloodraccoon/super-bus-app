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
        User user = userJpaRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Route route = routeJpaRepo.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route with ID " + routeId + " not found"));

        Ticket ticket = ticketMapper.toTicket(ticketDTO);

        ticket.setUser(user);
        ticket.setRoute(route);

        Ticket save = ticketJpaRepo.save(ticket);

        return ticketMapper.toTicketDTO(save);
    }

    @Override
    public List<TicketDTO> findByUserId(Long userId) {
        List<Ticket> byUserId = ticketJpaRepo.findByUserId(userId);

        return byUserId.stream()
                .map(ticketMapper::toTicketDTO)
                .toList();
    }
}
