package ru.ilezzov.group.flowgoods.finance.dto.category;

import ru.ilezzov.group.flowgoods.common.annotation.OptionalNotBlank;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionType;

public record TransactionCategoryUpdateDto(
    @OptionalNotBlank(message = "{validation.transaction_category.name.optional_not_blank}")
    @Trimmed
    String name,

    TransactionType type
){ }
