package ua.bus.app.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.bus.app.config.ServiceTestConfig;
import ua.bus.app.model.entity.User;
import ua.bus.app.model.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceTestConfig.class})
public class ServiceTestParent {
    @Autowired
    protected ObjectMapper objectMapper;

    protected List<User> testUsers;

    @BeforeEach
    void setUpParent() {
        initUsers();
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
}
