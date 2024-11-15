package ua.bus.app.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.bus.app.exception.UserAlreadyExistsException;
import ua.bus.app.exception.UserNotFoundException;
import ua.bus.app.model.dto.RegisterDTO;
import ua.bus.app.model.dto.UserDTO;
import ua.bus.app.model.entity.User;
import ua.bus.app.service.UserService;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/client/{id}")
    public String showClientProfile(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        UserDTO userById = userService.getUserById(id);
        model.addAttribute("user", userById);

        return "client-profile";
    }

    @GetMapping("/partner/{id}")
    public String showPartnerProfile(@PathVariable("id") Long id, Model model) throws UserNotFoundException {
        UserDTO userById = userService.getUserById(id);
        model.addAttribute("user", userById);

        return "partner-profile";
    }

    @PostMapping("/update/{id}")
    public String userUpdate(@PathVariable("id") Long id,
            @RequestParam String editName,
            @RequestParam String editEmail,
            @RequestParam String editPhone) throws UserNotFoundException {

        UserDTO userDTO = new UserDTO();
        userDTO.setName(editName);
        userDTO.setEmail(editEmail);
        userDTO.setPhone(editPhone);

        UserDTO updateUser = userService.updateUser(id, userDTO);

        if (updateUser.getRole().toString().equals("PARTNER")) {
            return "redirect:/user/partner/" + updateUser.getId();
        }

        return "redirect:/user/client/" + updateUser.getId();
    }

    @PostMapping("/delete/{id}")
    public String userDelete(@PathVariable("id") Long id) throws UserNotFoundException {
        userService.deleteUser(id);

        return "redirect:/";
    }
}
