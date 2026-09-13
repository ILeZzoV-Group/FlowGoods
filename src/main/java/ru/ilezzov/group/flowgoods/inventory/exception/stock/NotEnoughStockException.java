package ru.ilezzov.group.flowgoods.inventory.exception.stock;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class NotEnoughStockException extends BusinessException {
    public NotEnoughStockException(final UUID productUuid) {
        super("not-enough-stock", productUuid);
    }
}