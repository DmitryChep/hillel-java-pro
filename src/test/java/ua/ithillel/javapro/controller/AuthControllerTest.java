package ua.ithillel.javapro.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ua.ithillel.javapro.domain.dto.UserDTO;
import ua.ithillel.javapro.domain.model.User;
import ua.ithillel.javapro.service.UserDefaultService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserDefaultService userDefaultService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private AuthController authController;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userDTO = new UserDTO("John", "Doe", "john@example.com", "password123");
        user = new User();
        user.setEmail("john@example.com");
    }

    @Test
    void login_shouldReturnLoginPage() {
        String view = authController.login();
        assertEquals("login", view);
    }

    @Test
    void showRegistrationForm_shouldReturnRegisterPage() {
        when(model.addAttribute("user", UserDTO.userDTO())).thenReturn(model);
        String view = authController.showRegistrationForm(model);
        assertEquals("register", view);
    }

    @Test
    void registration_shouldReturnRedirect_whenEmailNotExists() {
        when(userDefaultService.findByEmail(userDTO.email())).thenReturn(null);
        when(userDefaultService.saveUser(userDTO)).thenReturn(user);

        String view = authController.registration(userDTO, bindingResult, model);
        assertEquals("redirect:/auth/register?success", view);
    }

    @Test
    void registration_shouldReturnRegisterPage_whenEmailExists() {
        when(userDefaultService.findByEmail(userDTO.email())).thenReturn(user);
        when(bindingResult.hasErrors()).thenReturn(true);

        String view = authController.registration(userDTO, bindingResult, model);
        assertEquals("register", view);
    }

    @Test
    void listRegisteredUsers_shouldReturnUsersPage() {
        when(userDefaultService.findAllUsers()).thenReturn(List.of(userDTO));
        when(model.addAttribute("users", List.of(userDTO))).thenReturn(model);

        String view = authController.listRegisteredUsers(model);
        assertEquals("users", view);
    }
}
