package ru.ilezzov.group.flowgoods.common.exception;

public class CursorEncodingFailedException extends BusinessException {
    public CursorEncodingFailedException() {
        super("cursor-encoding-failed");
    }
}