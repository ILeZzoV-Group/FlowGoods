package ru.ilezzov.group.flowgoods.common.exception;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {
    private final String errorCode;
    private final Object[] args;

    protected BusinessException(final String errorCode, final Object... args) {
        super("Error code: " + errorCode);
        this.errorCode = errorCode;
        this.args = args;
    }
}