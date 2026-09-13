package ru.ilezzov.group.flowgoods.finance.dto.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionCreateDto(
        @NotNull(message = "{validation.transaction.category_id.not_null}")
        UUID categoryId,

        @NotNull(message = "{validation.transaction.idempotency_key.not_null}")
        @Size(min = 3, max = 63, message = "{validation.transaction.idempotency_key.size}")
        @Trimmed
        String idempotencyKey,

        @NotNull(message = "{validation.transaction.type.not_null}")
        TransactionType type,

        @NotNull(message = "{validation.transaction.amount.not_null}")
        @PositiveOrZero(message = "{validation.transaction.amount.positive_or_zero}")
        BigDecimal amount,

        @Size(max = 1024, message = "{validation.transaction.description.size}")
        @Trimmed
        String description
) {
}
