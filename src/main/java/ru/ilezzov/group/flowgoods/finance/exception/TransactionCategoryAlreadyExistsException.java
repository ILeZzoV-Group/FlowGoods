package ru.ilezzov.group.flowgoods.finance.exception;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class TransactionCategoryAlreadyExistsException extends BusinessException {
    public TransactionCategoryAlreadyExistsException(final String name) {
        super("transaction-category-already-exists", name);
    }
}