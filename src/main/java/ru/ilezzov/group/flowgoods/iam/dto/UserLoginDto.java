package ru.ilezzov.group.flowgoods.iam.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginDto (
        @NotBlank(message = "{validation.user.email.not_blank}")
        @Email(message = "{validation.user.email.invalid}")
        String email,

        @NotBlank(message = "{validation.user.password.not_blank}")
        String password
) {}
