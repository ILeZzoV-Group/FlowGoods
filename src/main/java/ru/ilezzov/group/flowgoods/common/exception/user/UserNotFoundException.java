package ru.ilezzov.group.flowgoods.common.exception.user;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class UserNotFoundException extends BusinessException {
    public UserNotFoundException(final Long id) {
        super("user-not-found", id);
    }

    public UserNotFoundException(final UUID uuid) {
        super("user-not-found", uuid);
    }

    public UserNotFoundException(final String email) {
        super("user-not-found", email);
    }
}
