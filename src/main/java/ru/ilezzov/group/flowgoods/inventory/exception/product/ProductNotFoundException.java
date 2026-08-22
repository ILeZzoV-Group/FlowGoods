package ru.ilezzov.group.flowgoods.inventory.exception.product;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class ProductNotFoundException extends BusinessException {
    public ProductNotFoundException(final UUID uuid) {
        super("product-not-found", uuid);
    }
}
