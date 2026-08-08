package ru.ilezzov.group.flowgoods.inventory.dto.product.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CategoryCreateDto (
        @NotBlank
        @Size(min = 3, max = 63)
        String name,

        String description
) { }
