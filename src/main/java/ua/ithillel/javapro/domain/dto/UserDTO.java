package ua.ithillel.javapro.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


public record UserDTO(
        @Min(1) Long Id,
        @NotBlank(message = "Name cannot be blank") String name,
        @Email(message = "Email should be valid") String email) {}
