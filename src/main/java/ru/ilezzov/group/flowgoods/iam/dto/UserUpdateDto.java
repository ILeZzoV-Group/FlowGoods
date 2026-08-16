package ru.ilezzov.group.flowgoods.iam.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import ru.ilezzov.group.flowgoods.common.annotation.NotOfSpaces;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record UserUpdateDto (
        @Size(min = 3, max = 63, message = "{validation.user.username.size}")
        @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "{validation.user.username.invalid}")
        String username,

        @Size(max = 63, message = "{validation.user.firstname.size}")
        @NotOfSpaces(message = "{validation.user.firstname.not_of_spaces}")
        @Trimmed
        String firstName,

        @Size(max = 63, message = "{validation.user.secondname.size}")
        @NotOfSpaces(message = "{validation.user.secondname.not_of_spaces}")
        @Trimmed
        String secondName,

        @URL(message = "{validation.user.avatar.url.invalid}")
        String avatarUrl
) {}
