package ua.bus.app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ua.bus.app.model.dto.TicketDTO;
import ua.bus.app.model.entity.Ticket;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TicketMapper {
    TicketDTO toTicketDTO(Ticket ticket);
    Ticket toTicket(TicketDTO ticketDTO);
}
