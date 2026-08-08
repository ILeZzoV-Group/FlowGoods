package ru.ilezzov.group.flowgoods.inventory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierResponseDto;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SupplierMapper {
    @Mapping(source = "contact", target = ".")
    SupplierResponseDto toDto(Supplier supplier);
}
