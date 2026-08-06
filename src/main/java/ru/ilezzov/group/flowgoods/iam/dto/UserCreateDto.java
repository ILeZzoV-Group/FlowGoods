package ru.ilezzov.group.flowgoods.iam.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
        @NotBlank
        @Email
        @Size(min = 3, max = 255)
        String email,

        @NotBlank
        @Size(min = 3, max = 63)
        String password
) {
}
