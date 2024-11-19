package ua.bus.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import ua.bus.app.model.mapper.RouteMapper;
import ua.bus.app.model.mapper.RouteMapperImpl;

public class ServiceTestConfig {
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RouteMapper routeMapper() {
        return new RouteMapperImpl();
    }
}
