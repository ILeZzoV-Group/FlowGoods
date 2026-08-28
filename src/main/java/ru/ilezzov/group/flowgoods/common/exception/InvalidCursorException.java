package ru.ilezzov.group.flowgoods.common.exception;

public class InvalidCursorException extends BusinessException {
    public InvalidCursorException(final String cursor) {
        super("invalid-cursor", cursor);
    }
}