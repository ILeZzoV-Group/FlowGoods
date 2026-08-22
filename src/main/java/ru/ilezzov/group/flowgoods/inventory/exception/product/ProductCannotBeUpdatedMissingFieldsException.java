package ru.ilezzov.group.flowgoods.inventory.exception.product;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.List;
import java.util.UUID;

public class ProductCannotBeUpdatedMissingFieldsException extends BusinessException {
    public ProductCannotBeUpdatedMissingFieldsException(final UUID uuid, final List<String> missingFields) {
        super("product-cannot-be-updated-missing-fields", uuid, String.join(", ", missingFields));
    }
}