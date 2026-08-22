package ru.ilezzov.group.flowgoods.inventory.exception.product;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class CannotSuspendArchivedProductException extends BusinessException {
    public CannotSuspendArchivedProductException(final UUID uuid) {
        super("cannot-suspend-archived-product", uuid);
    }
}