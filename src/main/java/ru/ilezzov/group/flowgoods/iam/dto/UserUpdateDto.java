package ru.ilezzov.group.flowgoods.iam.dto;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record UserUpdateDto (
        @Size(min = 3, max = 63, message = "{validation.user.username.size}")
        String username,

        @Size(max = 63, message = "{validation.user.firstname.size}")
        String firstName,

        @Size(max = 63, message = "{validation.user.secondname.size}")
        String secondName,

        @URL(message = "{validation.user.avatar.url.invalid}")
        String avatarUrl
) {}
