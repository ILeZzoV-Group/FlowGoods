package ru.ilezzov.group.flowgoods.finance.dto.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionUpdateDto(
        UUID categoryId,

        TransactionType type,

        @PositiveOrZero(message = "{validation.transaction.amount.positive_or_zero}")
        BigDecimal amount,

        @Size(max = 1024, message = "{validation.transaction.description.size}")
        @Trimmed
        String description
) { }
