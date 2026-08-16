package ru.ilezzov.group.flowgoods.inventory.dto.product.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.ilezzov.group.flowgoods.common.annotation.NotOfSpaces;

public record CategoryCreateDto (
        @NotBlank(message = "{validation.category.name.not_blank}")
        @Size(min = 3, max = 63, message = "{validation.category.name.size}")
        String name,

        @Size(max = 1024, message = "{validation.category.description.size}")
        @NotOfSpaces(message = "{validation.category.description.not_of_spaces}")
        String description
) {}
