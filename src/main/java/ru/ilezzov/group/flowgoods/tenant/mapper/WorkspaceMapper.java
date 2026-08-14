package ru.ilezzov.group.flowgoods.tenant.mapper;


import org.mapstruct.*;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceCreateDto;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceResponseDto;
import ru.ilezzov.group.flowgoods.tenant.dto.WorkspaceUpdateDto;
import ru.ilezzov.group.flowgoods.tenant.entity.Workspace;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface WorkspaceMapper {
    WorkspaceResponseDto toDto(final Workspace workspace);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Workspace toEntity(final WorkspaceCreateDto workspaceCreateDto, final Long ownerId);

    void updateEntity(final WorkspaceUpdateDto dto, @MappingTarget final Workspace workspace);
}
