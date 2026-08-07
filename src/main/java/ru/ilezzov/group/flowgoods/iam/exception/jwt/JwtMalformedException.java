package ru.ilezzov.group.flowgoods.iam.exception.jwt;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class JwtMalformedException extends BusinessException {
    public JwtMalformedException() {
        super("jwt-malformed");
    }
}
