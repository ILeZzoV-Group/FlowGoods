package ru.ilezzov.group.flowgoods.inventory.dto.product;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import ru.ilezzov.group.flowgoods.common.annotation.OptionalNotBlank;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdateDto (
        @Size(min = 3, max = 255, message = "{validation.product.name.size}")
        @OptionalNotBlank(message = "{validation.product.name.optional_not_blank}")
        @Trimmed
        String name,

        @Size(min = 3, max = 63, message = "{validation.product.sku.size}")
        @Pattern(regexp = "^[a-zA-Z0-9\\\\-_]+$", message = "{validation.product.sku.invalid}")
        String sku,

        @PositiveOrZero(message = "{validation.product.price.positive_or_zero}")
        BigDecimal price,

        UUID categoryId,

        UUID marketplaceId,

        UUID supplierId,

        boolean publishImmediately
) {}
