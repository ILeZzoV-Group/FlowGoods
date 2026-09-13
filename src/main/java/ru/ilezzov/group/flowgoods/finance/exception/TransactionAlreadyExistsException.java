package ru.ilezzov.group.flowgoods.finance.exception;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class TransactionAlreadyExistsException extends BusinessException {
    public TransactionAlreadyExistsException(final String idempotencyKey) {
        super("transaction-already-exists", idempotencyKey);
    }
}