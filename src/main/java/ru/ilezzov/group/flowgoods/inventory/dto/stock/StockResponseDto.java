package ru.ilezzov.group.flowgoods.inventory.dto.stock;

import java.util.UUID;

public record StockResponseDto(
        UUID productId,
        int amount
) { }
