package ua.bus.app.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RouteDTO {
    private Long id;
    private String startLocation;
    private String endLocation;
    private String description;
    private Long price;
    private UserDTO partner;
    private List<StopDTO> stops;
}
