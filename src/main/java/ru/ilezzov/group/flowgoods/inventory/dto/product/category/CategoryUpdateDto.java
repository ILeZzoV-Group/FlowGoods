package ru.ilezzov.group.flowgoods.inventory.dto.product.category;

import jakarta.validation.constraints.Size;
import ru.ilezzov.group.flowgoods.common.annotation.NotOfSpaces;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record CategoryUpdateDto (
        @Size(min = 3, max = 63, message = "{validation.category.name.size}")
        @NotOfSpaces(message = "{validation.category.name.not_of_spaces}")
        @Trimmed
        String name,

        @Size(max = 1024, message = "{validation.category.description.size}")
        @Trimmed
        String description
) {}
