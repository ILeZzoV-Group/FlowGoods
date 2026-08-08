package ru.ilezzov.group.flowgoods.tenant.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceResponseDto;
import ru.ilezzov.group.flowgoods.tenant.entity.Workspace;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkspaceMapper {
    WorkspaceResponseDto toDto(final Workspace workspace);
}
