package ru.ilezzov.group.flowgoods.iam.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDto(
        @NotBlank(message = "{validation.user.username.not_blank}")
        @Size(min = 3, max = 63, message = "{validation.user.username.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "{validation.user.username.invalid}")
        String username,

        @NotBlank(message = "{validation.user.email.not_blank}")
        @Email(message = "{validation.user.email.invalid}")
        @Size(min = 3, max = 255, message = "{validation.user.email.size}")
        String email,

        @NotBlank(message = "{validation.user.password.not_blank}")
        @Size(min = 3, max = 63, message = "{validation.user.password.size}")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$", message = "{validation.user.password.invalid}")
        String password
) {}
