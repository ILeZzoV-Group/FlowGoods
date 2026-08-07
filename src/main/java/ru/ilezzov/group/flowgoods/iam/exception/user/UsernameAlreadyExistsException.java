package ru.ilezzov.group.flowgoods.iam.exception.user;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class UsernameAlreadyExistsException extends BusinessException {
    public UsernameAlreadyExistsException(final String username) {
        super("username-already-exists", username);
    }
}
