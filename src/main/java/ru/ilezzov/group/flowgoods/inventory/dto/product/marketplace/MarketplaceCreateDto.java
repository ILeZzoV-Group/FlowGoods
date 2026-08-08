package ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record MarketplaceCreateDto (
        @NotBlank(message = "{validation.marketplace.name.not_blank}")
        @Size(min = 3, max = 63, message = "{validation.marketplace.name.size}")
        String name,

        @NotBlank(message = "{validation.marketplace.url.not_blank}")
        @URL(message = "{validation.marketplace.url.invalid}")
        @Size(max = 255, message = "{validation.marketplace.url.size}")
        String url
) {}
