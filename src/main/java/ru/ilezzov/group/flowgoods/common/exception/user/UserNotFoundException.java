package ru.ilezzov.group.flowgoods.common.exception.user;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(final Long id) {
        super("user-not-found", id);
    }
}
