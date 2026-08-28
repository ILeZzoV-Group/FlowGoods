package ru.ilezzov.group.flowgoods.inventory.dto.supplier;

import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record SupplierFilterDto (
        @Trimmed
        String name
) {}
