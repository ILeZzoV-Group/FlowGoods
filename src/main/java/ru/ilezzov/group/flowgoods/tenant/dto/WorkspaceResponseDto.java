package ru.ilezzov.group.flowgoods.tenant.dto;

import java.time.Instant;
import java.util.UUID;

public record WorkspaceResponseDto (
        UUID uuid,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}
