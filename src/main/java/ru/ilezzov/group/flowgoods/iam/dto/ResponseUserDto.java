package ru.ilezzov.group.flowgoods.iam.dto;

import java.time.Instant;
import java.util.UUID;

public record ResponseUserDto(
        UUID uuid,
        String email,
        String username,
        String firstName,
        String secondName,
        String avatarUrl,
        Instant createdAt,
        Instant updatedAt
) {
}
