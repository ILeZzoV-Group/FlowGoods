package ru.ilezzov.group.flowgoods.inventory.dto.product.category;

import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record CategoryFilterDto (
        @Trimmed
        String name
){
}
