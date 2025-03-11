package ua.ithillel.javapro.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ua.ithillel.javapro.domain.dto.UserDTO;
import ua.ithillel.javapro.domain.model.User;
import ua.ithillel.javapro.service.UserDefaultService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserDefaultService userDefaultService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", UserDTO.userDTO());
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDTO userDTO,
                               BindingResult result,
                               Model model) {
        User existing = userDefaultService.findByEmail(userDTO.email());
        if (existing != null) {
            result.rejectValue("email", "email error", "The email already exists");
        }
        if (result.hasErrors()) {
            return "register";
        }
        userDefaultService.saveUser(userDTO);
        return "redirect:/auth/register?success";
    }

    @GetMapping("/users")
    public String listRegisteredUsers(Model model) {
        List<UserDTO> users = userDefaultService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }
}
