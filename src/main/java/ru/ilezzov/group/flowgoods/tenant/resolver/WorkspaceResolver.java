package ru.ilezzov.group.flowgoods.tenant.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.general.NotNullableException;
import ru.ilezzov.group.flowgoods.tenant.entity.Workspace;
import ru.ilezzov.group.flowgoods.tenant.exception.WorkspaceNotFoundException;
import ru.ilezzov.group.flowgoods.tenant.repository.WorkspaceRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WorkspaceResolver {
    private final WorkspaceRepository repository;

    public Workspace resolveByUuid(final UUID uuid) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        return this.repository.findByUuid(uuid)
                .orElseThrow(() -> new WorkspaceNotFoundException(uuid));
    }

    public Long resolveIdByUuid(final UUID uuid) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        return this.repository.findIdByUuid(uuid)
                .orElseThrow(() -> new WorkspaceNotFoundException(uuid));
    }

    public Workspace resolveByUuidAndOwnerId(final UUID uuid, final Long ownerId) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        if (ownerId == null) {
            throw new NotNullableException("ownerId");
        }

        return this.repository.findByUuidAndOwnerId(uuid, ownerId)
                .orElseThrow(() -> new WorkspaceNotFoundException(uuid));
    }

    public Long resolveIdByUuidAndOwnerId(final UUID uuid, final Long ownerId) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }
        if (ownerId == null) {
            throw new NotNullableException("ownerId");
        }

        return this.repository.findIdByUuidAndOwnerId(uuid, ownerId)
                .orElseThrow(() -> new WorkspaceNotFoundException(uuid));
    }
}
