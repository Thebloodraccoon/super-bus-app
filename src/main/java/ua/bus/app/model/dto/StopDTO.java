package ua.bus.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StopDTO {
    private Long id;
    private String locationName;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
}