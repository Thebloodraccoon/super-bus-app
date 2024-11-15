package ua.bus.app.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.UserDTO;
import ua.bus.app.service.UserService;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile/{id}")
    public String showUserProfile(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        UserDTO userById = userService.getUserById(id);
        model.addAttribute("user", userById);

        return "user-profile";
    }
}
