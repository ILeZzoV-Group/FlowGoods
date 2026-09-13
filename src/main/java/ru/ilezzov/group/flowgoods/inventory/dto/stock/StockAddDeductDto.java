package ru.ilezzov.group.flowgoods.inventory.dto.stock;

import jakarta.validation.constraints.Positive;

public record StockAddDeductDto(
        @Positive(message = "{validation.stock.amount.positive_or_zero}")
        int amount
) { }
