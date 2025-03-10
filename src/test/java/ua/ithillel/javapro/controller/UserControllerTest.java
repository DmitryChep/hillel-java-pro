package ua.ithillel.javapro.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ua.ithillel.javapro.domain.dto.PostDTO;
import ua.ithillel.javapro.domain.dto.UserDTO;
import ua.ithillel.javapro.domain.dto.UserPostBindingDTO;
import ua.ithillel.javapro.service.interfaces.UserService;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    public void getAllUsersByEmailDomain_shouldReturnList_whenUsersExist() throws Exception {
        String domain = "example.com";
        UserDTO userDTO = new UserDTO(1L, "John", "john@example.com");
        List<UserDTO> userDTOs = Collections.singletonList(userDTO);

        when(userService.getAllUsersByEmailDomain(domain)).thenReturn(userDTOs);

        mockMvc.perform(get("/api/users/email-domain/{domain}", domain)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"));
    }

    @Test
    public void getUserByName_shouldReturnUser_whenUserExists() throws Exception {
        String userName = "John";
        UserDTO userDTO = new UserDTO(1L, "John", "john@example.com");

        when(userService.getUserByName(userName)).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/{userName}", userName)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void createUser_shouldReturnUser_whenUserIsCreated() throws Exception {
        when(userService.createUser(any(UserDTO.class), any(PostDTO.class)))
                .thenReturn(new UserDTO(1L, "John", "john@example.com"));

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userDTO\":{\"id\":1, \"name\":\"John\", \"email\":\"john@example.com\"}, \"postDTO\":{\"id\":1, \"title\":\"Test\", \"content\":\"Test Post\"}}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    public void deleteUser_shouldReturnUser_whenUserIsDeleted() throws Exception {
        Long id = 1L;
        UserDTO deletedUserDTO = new UserDTO(1L, "John", "john@example.com");

        when(userService.deleteUser(id)).thenReturn(deletedUserDTO);

        mockMvc.perform(delete("/api/users/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }
}
