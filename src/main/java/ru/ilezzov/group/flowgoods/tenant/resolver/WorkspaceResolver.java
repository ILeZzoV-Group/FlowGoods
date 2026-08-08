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

    public Workspace resolverByUuid(final UUID uuid) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        return this.repository.findByUuid(uuid)
                .orElseThrow(() -> new WorkspaceNotFoundException(uuid));
    }

    public Long resolverIdByUuid(final UUID uuid) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        return this.repository.findIdByUuid(uuid)
                .orElseThrow(() -> new WorkspaceNotFoundException(uuid));
    }
}
