package ru.ilezzov.group.flowgoods.finance.exception;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class TransactionCategoryNotFoundException extends BusinessException {
    public TransactionCategoryNotFoundException(final UUID uuid) {
        super("transaction-category-not-found", uuid);
    }
}