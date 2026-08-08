package ru.ilezzov.group.flowgoods.tenant.exception;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class WorkspaceNotFoundException extends BusinessException {
    public WorkspaceNotFoundException(final UUID uuid) {
        super("workspace-not-found", uuid);
    }
}
