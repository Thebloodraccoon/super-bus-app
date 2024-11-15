package ua.bus.app.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteItemDTO {
    private Long id;
    private String name;
    private String startLocation;
    private String endLocation;
    private String description;
    private Long price;
}