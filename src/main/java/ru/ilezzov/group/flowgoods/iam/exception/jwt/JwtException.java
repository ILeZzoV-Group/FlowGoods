package ru.ilezzov.group.flowgoods.iam.exception.jwt;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class JwtException extends BusinessException {
    public JwtException(final String jwt) {
        super("jwt-error", jwt);
    }
}
