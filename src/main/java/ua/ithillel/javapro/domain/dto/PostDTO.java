package ua.ithillel.javapro.domain.dto;

import jakarta.validation.constraints.Min;

public record PostDTO(
        @Min(1) Long id,
        String title,
        String content) {
}
