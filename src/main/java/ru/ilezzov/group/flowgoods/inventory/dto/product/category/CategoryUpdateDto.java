package ru.ilezzov.group.flowgoods.inventory.dto.product.category;

import jakarta.validation.constraints.Size;

public record CategoryUpdateDto (
        @Size(min = 3, max = 63, message = "{validation.category.name.size}")
        String name,

        @Size(max = 1024, message = "{validation.category.description.size}")
        String description
) {}
