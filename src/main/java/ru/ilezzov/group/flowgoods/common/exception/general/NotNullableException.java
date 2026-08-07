package ru.ilezzov.group.flowgoods.common.exception.general;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class NotNullableException extends BusinessException {
    public NotNullableException(final String param) {
        super("parameter-cannot-be-null", param);
    }
}
