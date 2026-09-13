package ru.ilezzov.group.flowgoods.finance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.ilezzov.group.flowgoods.common.mapper.GlobalMapperConfig;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryCreateDto;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryResponseDto;
import ru.ilezzov.group.flowgoods.finance.dto.category.TransactionCategoryUpdateDto;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionCategory;

@Mapper(config = GlobalMapperConfig.class)
public interface TransactionCategoryMapper {
    TransactionCategoryResponseDto toDto(TransactionCategory category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    TransactionCategory toEntity(final TransactionCategoryCreateDto dto, final Long workspaceId);

    void updateEntity(final TransactionCategoryUpdateDto dto, @MappingTarget final TransactionCategory category);
}
