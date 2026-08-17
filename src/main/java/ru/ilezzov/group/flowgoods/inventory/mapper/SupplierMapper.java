package ru.ilezzov.group.flowgoods.inventory.mapper;

import org.mapstruct.*;
import ru.ilezzov.group.flowgoods.common.mapper.GlobalMapperConfig;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier;

@Mapper(config = GlobalMapperConfig.class)
public interface SupplierMapper {
    @Mapping(source = "contact", target = ".")
    SupplierResponseDto toDto(Supplier supplier);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Supplier toEntity(final SupplierCreateDto dto, final Long workspaceId);

    @Mapping(target = "contact.phone", source = "phone")
    @Mapping(target = "contact.email", source = "email")
    @Mapping(target = "contact.link", source = "link")
    void updateEntity(final SupplierUpdateDto dto, @MappingTarget final Supplier supplier);
}
