package ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace;

import java.time.Instant;
import java.util.UUID;

public record MarketplaceResponseDto(
        UUID uuid,
        String name,
        String url,
        Instant createdAt,
        Instant updatedAt
) {
}
