package ru.ilezzov.group.flowgoods.common.exception.user;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class UserAlreadyExists extends BusinessException {
    public UserAlreadyExists(final String email) {
        super("user-already-exists", email);
    }
}
