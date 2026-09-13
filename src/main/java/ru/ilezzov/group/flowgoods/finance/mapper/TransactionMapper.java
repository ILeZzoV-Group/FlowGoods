package ru.ilezzov.group.flowgoods.finance.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.ilezzov.group.flowgoods.common.mapper.GlobalMapperConfig;
import ru.ilezzov.group.flowgoods.finance.dto.transaction.TransactionCreateDto;
import ru.ilezzov.group.flowgoods.finance.dto.transaction.TransactionResponseDto;
import ru.ilezzov.group.flowgoods.finance.dto.transaction.TransactionUpdateDto;
import ru.ilezzov.group.flowgoods.finance.entity.Transaction;
import ru.ilezzov.group.flowgoods.finance.resolver.TransactionCategoryResolver;

@Mapper(
        config = GlobalMapperConfig.class,
        uses = {
                TransactionCategoryResolver.class
        }
)
public interface TransactionMapper {
    TransactionResponseDto toDto(Transaction transaction);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "workspaceId", source = "workspaceId")
    @Mapping(target = "category", source = "dto.categoryId")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "version", ignore = true)
    Transaction toEntity(final TransactionCreateDto dto, final Long workspaceId, @Context final Long contextWorkspaceId);

    @Mapping(source = "dto.categoryId", target = "category")
    @Mapping(target = "workspaceId", ignore = true)
    void updateEntity(final TransactionUpdateDto dto, @MappingTarget final Transaction transaction, @Context final Long contextWorkspaceId);
}
