package ru.ilezzov.group.flowgoods.tenant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.iam.entity.User;
import ru.ilezzov.group.flowgoods.iam.resolver.UserResolver;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceCreateDto;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceResponseDto;
import ru.ilezzov.group.flowgoods.tenant.entity.Workspace;
import ru.ilezzov.group.flowgoods.tenant.exception.WorkspaceAlreadyExistsException;
import ru.ilezzov.group.flowgoods.tenant.mapper.WorkspaceMapper;
import ru.ilezzov.group.flowgoods.tenant.repository.WorkspaceRepository;
import ru.ilezzov.group.flowgoods.tenant.resolver.WorkspaceResolver;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMapper workspaceMapper;
    private final WorkspaceResolver workspaceResolver;

    private final UserResolver userResolver;

    @Transactional(readOnly = true)
    public WorkspaceResponseDto getWorkspaceByUuid(final UUID uuid) {
        return this.workspaceMapper.toDto(
                this.workspaceResolver.resolverByUuid(uuid)
        );
    }

    public WorkspaceResponseDto createWorkspace(final WorkspaceCreateDto dto, final UUID ownerUuid) {
        final User user = this.userResolver.resolveUserByUuid(ownerUuid);

        if (this.workspaceRepository.existsByNameAndOwnerId(dto.name(), user.getId())) {
            throw new WorkspaceAlreadyExistsException(dto.name());
        }

        final Workspace workspace = new Workspace(dto.name(), user.getId());

        return this.workspaceMapper.toDto(
                this.workspaceRepository.save(workspace)
        );
    }
}
