package ru.ilezzov.group.flowgoods.finance.dto.transaction;

import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public record TransactionFilterDto(
    UUID categoryId,

    TransactionType type,

    Instant from,

    Instant to,

    String cursor,

    Integer limit
){
    public TransactionFilterDto {
        if (limit == null) {
            limit = 20;
        }

        if (limit > 100) {
            limit = 100;
        }
    }
}
