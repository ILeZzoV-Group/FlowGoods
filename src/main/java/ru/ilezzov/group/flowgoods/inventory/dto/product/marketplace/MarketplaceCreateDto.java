package ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record MarketplaceCreateDto (
        @NotBlank
        @Size(min = 3, max = 63)
        String name,

        @NotBlank
        @URL
        String url
) { }
