package ru.ilezzov.group.flowgoods.inventory.dto.product;

import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierResponseDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.ProductStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponseDto (
        UUID uuid,
        String name,
        String sku,
        ProductStatus status,
        BigDecimal price,
        CategoryResponseDto category,
        MarketplaceResponseDto marketplace,
        SupplierResponseDto supplier,
        Instant createdAt,
        Instant updatedAt
) {
}
