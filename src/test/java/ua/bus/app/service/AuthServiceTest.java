package ua.bus.app.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ua.bus.app.exception.InvalidPasswordException;
import ua.bus.app.exception.UserAlreadyExistsException;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.LoginDTO;
import ua.bus.app.model.dto.RegisterDTO;
import ua.bus.app.model.entity.User;
import ua.bus.app.repo.UserJpaRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AuthServiceTest extends ServiceTestParent {
    @Mock
    private UserJpaRepo userJpaRepoMock;

    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        authService = new AuthService(userJpaRepoMock);
    }

    @Test
    void testLoginUser_Success() throws Exception {
        LoginDTO loginDTO = new LoginDTO("john.doe@example.com", "$2a$10$wz1vX7yhmTz5UQmOZlBjOeQ0OEYBd.RiLdUVbNGa7HyV0dfbUbIaK");
        User mockUser = testUsers.get(0);
        when(userJpaRepoMock.findByEmail("john.doe@example.com"))
                .thenReturn(Optional.of(mockUser));


        User loggedInUser = authService.loginUser(loginDTO);

        assertNotNull(loggedInUser);
        assertEquals("john.doe@example.com", loggedInUser.getEmail());
        Mockito.verify(userJpaRepoMock).findByEmail("john.doe@example.com");
    }

    @Test
    void testLoginUser_InvalidPassword() throws Exception {
        LoginDTO loginDTO = new LoginDTO("john.doe@example.com", ".sdfsdf");
        User mockUser = testUsers.get(0);
        when(userJpaRepoMock.findByEmail("john.doe@example.com"))
                .thenReturn(Optional.of(mockUser));

        assertThrows(InvalidPasswordException.class, () -> authService.loginUser(loginDTO));
        Mockito.verify(userJpaRepoMock).findByEmail("john.doe@example.com");
    }

    @Test
    void testLoginUser_InvalidLogin() throws Exception {
        LoginDTO loginDTO = new LoginDTO("nonexistent@example.com", ".sdfsdf");
        User mockUser = testUsers.get(0);
        when(userJpaRepoMock.findByEmail("nonexistent@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.loginUser(loginDTO));
        Mockito.verify(userJpaRepoMock).findByEmail("nonexistent@example.com");
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("newuser@example.com", "New User", "+380123456789", "password123", "CLIENT");
        when(userJpaRepoMock.findByEmail("newuser@example.com")).thenReturn(Optional.empty());
        User expectedUser = authService.mapRegisterDTOToUser(registerDTO);

        when(userJpaRepoMock.save(Mockito.any(User.class))).thenReturn(expectedUser);

        User registeredUser = authService.registerUser(registerDTO);

        assertNotNull(registeredUser);
        assertEquals(registerDTO.getEmail(), registeredUser.getEmail());
        Mockito.verify(userJpaRepoMock).save(Mockito.any(User.class));
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        RegisterDTO registerDTO = new RegisterDTO("john.doe@example.com", "John Doe", "+380123456789", "password123", "CLIENT");
        when(userJpaRepoMock.findByEmail("john.doe@example.com")).thenReturn(Optional.of(testUsers.get(0)));
//        assertThrows(UserAlreadyExistsException.class, () -> authService.registerUser(registerDTO));
//        Mockito.verify(userJpaRepoMock).findByEmail("john.doe@example.com");
    }
}