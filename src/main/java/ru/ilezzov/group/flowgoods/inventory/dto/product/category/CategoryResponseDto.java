package ru.ilezzov.group.flowgoods.inventory.dto.product.category;

import java.time.Instant;
import java.util.UUID;

public record CategoryResponseDto(
        UUID uuid,
        String name,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
