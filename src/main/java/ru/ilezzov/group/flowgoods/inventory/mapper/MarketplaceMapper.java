package ru.ilezzov.group.flowgoods.inventory.mapper;

import org.mapstruct.*;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Marketplace;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface MarketplaceMapper {
    MarketplaceResponseDto toDto(Marketplace marketplace);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Marketplace toEntity(final MarketplaceCreateDto dto, final Long workspaceId);

    void updateEntity(final MarketplaceUpdateDto dto, @MappingTarget final Marketplace marketplace);
}
