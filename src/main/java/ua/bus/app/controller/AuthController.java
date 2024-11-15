package ua.bus.app.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.bus.app.exception.PasswordEncryptionException;
import ua.bus.app.model.dto.LoginDTO;
import ua.bus.app.model.dto.RegisterDTO;
import ua.bus.app.service.AuthService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/user/login")
    public String handleLogin(@RequestParam String username, @RequestParam String password, Model model) throws PasswordEncryptionException {
        LoginDTO loginDTO = new LoginDTO(username, password);
        Long user = authService.loginUser(loginDTO);


        if (user != 0) {
            return "redirect:/user/" + user;
        }

        model.addAttribute("error", "Невірне ім'я користувача або пароль");
        return "index";
    }


    @PostMapping("/user/register")
    public String handleRegister(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String password,
            @RequestParam String role,
            Model model) throws PasswordEncryptionException {

        RegisterDTO registerDTO = new RegisterDTO(username, email, phone, password, role);
        Long userId = authService.registerUser(registerDTO);

        if (userId != 0) {
            return "redirect:/user/" + userId;
        } else {
            model.addAttribute("error", "Невірне ім'я користувача або інша помилка реєстрації");
            return "index";
        }
    }


}
