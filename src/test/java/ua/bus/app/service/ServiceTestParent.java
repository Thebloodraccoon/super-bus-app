package ua.bus.app.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.bus.app.config.ServiceTestConfig;
import ua.bus.app.model.entity.Route;
import ua.bus.app.model.entity.User;
import ua.bus.app.model.mapper.RouteMapper;
import ua.bus.app.model.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class ServiceTestParent {
    @Autowired
    protected ObjectMapper objectMapper;
    @Autowired
    protected RouteMapper routeMapper;

    protected List<User> testUsers;
    protected List<Route> testRoutes;

    @BeforeEach
    void setUpParent() {
        initUsers();
        initRoutes();
    }

    private void initUsers() {
        try(final InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("user-data.json");

        ) {

            testUsers = objectMapper.readValue(inputStream, new TypeReference<List<User>>() {
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initRoutes() {
        try(final InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("route-data.json");

        ) {

            testRoutes = objectMapper.readValue(inputStream, new TypeReference<List<Route>>() {
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
