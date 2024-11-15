package ua.bus.app.model.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ua.bus.app.model.dto.RouteDTO;
import ua.bus.app.model.dto.RouteItemDTO;
import ua.bus.app.model.entity.Route;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RouteMapper {
    RouteDTO toRouteDTO(Route route);
    RouteItemDTO toRouteItemDTO(Route route);
    Route toRoute(RouteDTO routeDTO);
}
