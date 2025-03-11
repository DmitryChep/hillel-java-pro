package ua.ithillel.javapro.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleDTO (
        @Min(1) Long id,
        @NotBlank @NotNull String name){}
