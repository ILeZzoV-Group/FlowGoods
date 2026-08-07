package ru.ilezzov.group.flowgoods.common.exception.password;

import org.hibernate.usertype.BaseUserTypeSupport;
import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class PasswordDoNotMatchException extends BusinessException {
    public PasswordDoNotMatchException() {
        super("passwords-do-not-match");
    }
}
