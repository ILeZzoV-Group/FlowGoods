package ru.ilezzov.group.flowgoods.finance.dto.transaction;

import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryResponseDto;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionResponseDto(
        UUID uuid,
        TransactionCategoryResponseDto category,
        TransactionType type,
        BigDecimal amount,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
