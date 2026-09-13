package ru.ilezzov.group.flowgoods.inventory.exception.stock;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class StockNotFoundException extends BusinessException {
    public StockNotFoundException(final UUID productUuid) {
        super("stock-not-found", productUuid);
    }

}