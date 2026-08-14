package ru.ilezzov.group.flowgoods.inventory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier;
import ru.ilezzov.group.flowgoods.tenant.entity.Workspace;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SupplierMapper {
    @Mapping(source = "contact", target = ".")
    SupplierResponseDto toDto(Supplier supplier);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Supplier toEntity(final SupplierCreateDto dto, final Long workspaceId);

    void updateSupplier(final SupplierUpdateDto dto, @MappingTarget final Supplier supplier);
}
