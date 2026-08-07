package ru.ilezzov.group.flowgoods.iam.exception.password;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class PasswordDoNotMatchException extends BusinessException {
    public PasswordDoNotMatchException() {
        super("passwords-do-not-match");
    }
}
