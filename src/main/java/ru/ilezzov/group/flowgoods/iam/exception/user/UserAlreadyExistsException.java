package ru.ilezzov.group.flowgoods.iam.exception.user;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class UserAlreadyExistsException extends BusinessException {
    public UserAlreadyExistsException(final String email) {
        super("user-already-exists", email);
    }
}
