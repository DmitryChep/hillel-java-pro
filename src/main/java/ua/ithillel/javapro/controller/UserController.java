package ua.ithillel.javapro.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.ithillel.javapro.domain.dto.UserDTO;
import ua.ithillel.javapro.domain.dto.UserPostBindingDTO;
import ua.ithillel.javapro.service.interfaces.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/email-domain/{domain}")
    public ResponseEntity<List<UserDTO>> getUsersByEmailDomain(@PathVariable String domain) {
        List<UserDTO> users = userService.getAllUsersByEmailDomain(domain);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<UserDTO> getUserByName(@PathVariable @NotBlank String userName) {
        UserDTO userDTO = userService.getUserByName(userName);
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserPostBindingDTO userPostBindingDTO) {
        UserDTO createdUser = userService.createUser(userPostBindingDTO.userDTO(), userPostBindingDTO.postDTO());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable @Valid Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable @Valid Long id) {
        UserDTO deletedUser = userService.deleteUser(id);
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }
}
