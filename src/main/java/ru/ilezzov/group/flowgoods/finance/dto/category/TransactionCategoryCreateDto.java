package ru.ilezzov.group.flowgoods.finance.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionType;

public record TransactionCategoryCreateDto(
        @NotNull(message = "{validation.transaction_category.type.not_null}")
        TransactionType type,

        @NotBlank(message = "{validation.transaction_category.name.not_blank}")
        @Size(min = 3, max = 255, message = "{validation.transaction_category.name.size}")
        @Trimmed
        String name
) {
}
