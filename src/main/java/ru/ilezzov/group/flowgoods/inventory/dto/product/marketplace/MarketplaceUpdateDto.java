package ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace;

import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import ru.ilezzov.group.flowgoods.common.annotation.NotOfSpaces;

public record MarketplaceUpdateDto (
        @Size(min = 3, max = 63, message = "{validation.marketplace.name.size}")
        @NotOfSpaces(message = "{validation.marketplace.name.not_of_spaces}")
        String name,

        @URL(message = "{validation.marketplace.url.invalid}")
        @Size(max = 255, message = "{validation.marketplace.url.size}")
        String url
) { }
