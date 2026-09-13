package ru.ilezzov.group.flowgoods.finance.exception;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class TransactionNotFoundException extends BusinessException {
    public TransactionNotFoundException(final UUID uuid) {
        super("transaction-not-found", uuid);
        }
}