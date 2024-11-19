package ua.bus.app.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.bus.app.exception.InvalidPasswordException;
import ua.bus.app.exception.PasswordEncryptionException;
import ua.bus.app.exception.UserAlreadyExistsException;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.LoginDTO;
import ua.bus.app.model.dto.RegisterDTO;
import ua.bus.app.model.entity.User;
import ua.bus.app.service.AuthService;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/user/login")
    public String handleLogin(@RequestParam String loginEmail,
                              @RequestParam String loginPassword,
                              Model model) throws PasswordEncryptionException, UserNotFoundException, InvalidPasswordException {
        LoginDTO loginDTO = new LoginDTO(loginEmail, loginPassword);
        User user = authService.loginUser(loginDTO);


        if (user.getRole().toString().equals("CLIENT")) {
            return "redirect:/user/client/" + user.getId();
        }

        if (user.getRole().toString().equals("PARTNER")) {
            return "redirect:/user/partner/" + user.getId();
        }

        model.addAttribute("error", "Невірне ім'я користувача або пароль");
        return "index";
    }


    @PostMapping("/user/register")
    public String handleRegister(
            @RequestParam String registerName,
            @RequestParam String registerEmail,
            @RequestParam String registerPhone,
            @RequestParam String registerPassword,
            @RequestParam String registerRole,
            Model model) throws UserAlreadyExistsException {

        RegisterDTO registerDTO = new RegisterDTO(registerName, registerEmail, registerPhone, registerPassword, registerRole);
        User user = authService.registerUser(registerDTO);
        if (user.getRole().toString().equals("CLIENT")) {
            return "redirect:/user/client/" + user.getId();
        }

        if (user.getRole().toString().equals("PARTNER")) {
            return "redirect:/user/partner/" + user.getId();
        }

        model.addAttribute("error", "Невірне ім'я користувача або інша помилка реєстрації");
        return "index";
    }


}
