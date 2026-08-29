package ru.ilezzov.group.flowgoods.inventory.dto.filter;

import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;
import ru.ilezzov.group.flowgoods.inventory.entity.product.ProductStatus;

import java.util.UUID;

public record ProductFilterDto (
        @Trimmed
        String name,

        @Trimmed
        String sku,

        ProductStatus status,

        UUID categoryId,

        UUID marketplaceId,

        UUID supplierId,

        String cursor,

        Integer limit
) {
    public ProductFilterDto {
        if (limit == null) {
            limit = 20;
        }

        if (limit > 100) {
            limit = 100;
        }
    }
}
