package ru.ilezzov.group.flowgoods.tenant.exception;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class WorkspaceAlreadyExistsException extends BusinessException {
    public WorkspaceAlreadyExistsException(final String name) {
        super("workspace-already-exists", name);
    }
}
