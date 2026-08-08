package ru.ilezzov.group.flowgoods.inventory.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.ilezzov.group.flowgoods.inventory.dto.product.category.CategoryResponseDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Category;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);
}
