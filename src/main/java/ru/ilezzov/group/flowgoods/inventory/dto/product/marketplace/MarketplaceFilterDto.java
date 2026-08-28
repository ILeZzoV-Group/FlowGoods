package ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace;

import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record MarketplaceFilterDto (
        @Trimmed
        String name
) {
}
