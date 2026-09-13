package ru.ilezzov.group.flowgoods.finance.dto.category;

import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionType;

public record TransactionCategoryFilterDto(
    @Trimmed
    String name,

    TransactionType type,

    String cursor,

    Integer limit
){
    public TransactionCategoryFilterDto {
        if (limit == null) {
            limit = 20;
        }

        if (limit > 100) {
            limit = 100;
        }
    }
}
