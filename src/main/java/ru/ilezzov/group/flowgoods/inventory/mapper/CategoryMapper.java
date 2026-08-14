package ru.ilezzov.group.flowgoods.inventory.mapper;

import org.mapstruct.*;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Category toEntity(final CategoryCreateDto dto, final Long workspaceId);

    void updateEntity(final CategoryUpdateDto dto, @MappingTarget final Category category);
}
