package ru.ilezzov.group.flowgoods.inventory.exception.product;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class ProductAlreadyExistsException extends BusinessException {
    public ProductAlreadyExistsException(final String sku) {
        super("product-already-exists", sku);
    }
}
