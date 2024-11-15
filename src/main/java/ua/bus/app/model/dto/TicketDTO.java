package ua.bus.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {
    private Long id;
    private UserDTO userDTO;
    private RouteDTO routeDTO;
    private LocalDateTime purchaseDate;
    private String status;
    private Integer seats;
    private Integer durationHours;
}