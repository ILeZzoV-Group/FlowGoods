package ru.ilezzov.group.flowgoods.inventory.exception.product;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.List;
import java.util.UUID;

public class ProductCannotBePublishedMissingFieldsException extends BusinessException {
    public ProductCannotBePublishedMissingFieldsException(final UUID uuid, final List<String> missingFields) {
        super("product-cannot-be-published-missing-fields", uuid, String.join(", ", missingFields));
    }
}