package ru.ilezzov.group.flowgoods.common.exception.jwt;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class JwtExpiredException extends BusinessException {
    public JwtExpiredException() {
        super("jwt-expired");
    }
}
