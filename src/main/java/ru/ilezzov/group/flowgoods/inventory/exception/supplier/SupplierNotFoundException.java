package ru.ilezzov.group.flowgoods.inventory.exception.supplier;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class SupplierNotFoundException extends BusinessException {
    public SupplierNotFoundException(final UUID uuid) {
        super("supplier-not-found", uuid);
    }
}
