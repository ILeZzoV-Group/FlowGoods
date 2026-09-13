package ru.ilezzov.group.flowgoods.finance.dto.category;

import ru.ilezzov.group.flowgoods.finance.entity.TransactionType;

import java.time.Instant;
import java.util.UUID;

public record TransactionCategoryResponseDto(
        UUID uuid,
        TransactionType type,
        String name,
        Instant createdAt,
        Instant updatedAt
) {
}
