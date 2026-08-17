package ru.ilezzov.group.flowgoods.tenant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceCreateDto;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceResponseDto;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceUpdateDto;
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

    @Transactional(readOnly = true)
    public WorkspaceResponseDto getWorkspaceByUuid(final UUID uuid) {
        return this.workspaceMapper.toDto(
                this.workspaceResolver.resolveByUuid(uuid)
        );
    }

    @Transactional(readOnly = true)
    public WorkspaceResponseDto getWorkspaceByUuidAndOwnerId(final UUID uuid, final Long ownerId) {
        return this.workspaceMapper.toDto(
                this.workspaceResolver.resolveByUuidAndOwnerId(uuid, ownerId)
        );
    }

    public WorkspaceResponseDto createWorkspace(final WorkspaceCreateDto dto, final Long ownerId) {
        if (this.workspaceRepository.existsByNameIgnoreCaseAndOwnerId(dto.name(), ownerId)) {
            throw new WorkspaceAlreadyExistsException(dto.name());
        }

        final Workspace workspace = this.workspaceMapper.toEntity(dto, ownerId);

        return this.workspaceMapper.toDto(
                this.workspaceRepository.save(workspace)
        );
    }

    public WorkspaceResponseDto updateWorkspace(final WorkspaceUpdateDto dto, final UUID uuid, final Long ownerId) {
        final Workspace workspace = this.workspaceResolver.resolveByUuidAndOwnerId(uuid, ownerId);

        if (workspace.getName().equals(dto.name())) {
            return this.workspaceMapper.toDto(workspace);
        }

        if (!workspace.getName().equalsIgnoreCase(dto.name())) {
            if (this.workspaceRepository.existsByNameIgnoreCaseAndOwnerId(dto.name().toLowerCase(), ownerId)) {
                throw new WorkspaceAlreadyExistsException(dto.name());
            }
        }

        this.workspaceMapper.updateEntity(dto, workspace);
        return this.workspaceMapper.toDto(workspace);
    }
}
