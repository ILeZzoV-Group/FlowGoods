package ru.ilezzov.group.flowgoods.common.exception.jwt;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class JwtSubjectExtractionFailedException extends BusinessException {
    public JwtSubjectExtractionFailedException() {
        super("jwt-subject-extraction-failed");
    }
}
