package ua.ithillel.javapro.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotBlank @NotNull String firstName,
        @NotBlank @NotNull String lastName,
        @NotBlank @NotNull String email,
        @NotBlank @NotNull String password) {
    public static UserDTO userDTO() {
        return new UserDTO("","","","");
    }
}
