package ru.ilezzov.group.flowgoods.inventory.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.ilezzov.group.flowgoods.common.mapper.BlankStringToNullMapper;
import ru.ilezzov.group.flowgoods.common.mapper.GlobalMapperConfig;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Product;
import ru.ilezzov.group.flowgoods.inventory.repository.SupplierRepository;
import ru.ilezzov.group.flowgoods.inventory.resolver.CategoryResolver;
import ru.ilezzov.group.flowgoods.inventory.resolver.MarketplaceResolver;
import ru.ilezzov.group.flowgoods.inventory.resolver.SupplierResolver;

@Mapper(
        config = GlobalMapperConfig.class,
        uses = {
                SupplierResolver.class,
                MarketplaceResolver.class,
                CategoryResolver.class,

                CategoryMapper.class,
                MarketplaceMapper.class,
                SupplierMapper.class,
        }
)
public interface ProductMapper {
    ProductResponseDto toDto(final Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "workspaceId", source = "workspaceId")
    @Mapping(target = "category", source = "dto.categoryId")
    @Mapping(target = "marketplace", source = "dto.marketplaceId")
    @Mapping(target = "supplier", source = "dto.supplierId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Product toEntity(final ProductCreateDto dto, final Long workspaceId, @Context final Long workspaceIdContext);

    @Mapping(source = "dto.categoryId", target = "category")
    @Mapping(source = "dto.marketplaceId", target = "marketplace")
    @Mapping(source = "dto.supplierId", target = "supplier")
    @Mapping(target = "workspaceId", ignore = true)
    void updateEntity(final ProductUpdateDto dto, @MappingTarget Product product, @Context final Long workspaceId);
}
