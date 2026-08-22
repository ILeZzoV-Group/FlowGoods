package ru.ilezzov.group.flowgoods.inventory.exception.product;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class CannotPublishArchivedProductException extends BusinessException {
    public CannotPublishArchivedProductException(final UUID uuid) {
        super("cannot-publish-archived-product", uuid);
    }
}