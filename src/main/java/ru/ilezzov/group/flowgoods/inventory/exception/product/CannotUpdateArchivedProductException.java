package ru.ilezzov.group.flowgoods.inventory.exception.product;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class CannotUpdateArchivedProductException extends BusinessException {
    public CannotUpdateArchivedProductException(final UUID uuid) {
        super("cannot-update-archived-product", uuid);
    }
}