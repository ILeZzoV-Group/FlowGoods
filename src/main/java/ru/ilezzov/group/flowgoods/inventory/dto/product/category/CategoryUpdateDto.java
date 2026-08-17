package ru.ilezzov.group.flowgoods.inventory.dto.product.category;

import jakarta.validation.constraints.Size;
import ru.ilezzov.group.flowgoods.common.annotation.OptionalNotBlank;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record CategoryUpdateDto (
        @Size(min = 3, max = 63, message = "{validation.category.name.size}")
        @OptionalNotBlank(message = "{validation.category.name.optional_not_blank}")
        @Trimmed
        String name,

        @Size(max = 1024, message = "{validation.category.description.size}")
        @Trimmed
        String description
) {}
