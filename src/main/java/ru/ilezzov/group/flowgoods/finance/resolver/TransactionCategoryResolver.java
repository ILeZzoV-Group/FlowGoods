package ru.ilezzov.group.flowgoods.finance.resolver;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.general.NotNullableException;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionCategory;
import ru.ilezzov.group.flowgoods.finance.exception.TransactionCategoryNotFoundException;
import ru.ilezzov.group.flowgoods.finance.repository.TransactionCategoryRepository;
import ru.ilezzov.group.flowgoods.inventory.exception.category.CategoryNotFoundException;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionCategoryResolver {
    private final TransactionCategoryRepository repository;

    public TransactionCategory resolveByUuidAndWorkspaceId(final UUID uuid, @Context final Long workspaceId) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        if (workspaceId == null) {
            throw new NotNullableException("workspaceId");
        }

        return this.repository.findByUuidAndWorkspaceId(uuid, workspaceId)
                .orElseThrow(() -> new TransactionCategoryNotFoundException(uuid));
    }

    public Long resolveIdByUuidAndWorkspaceId(final UUID uuid, final Long workspaceId) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        if (workspaceId == null) {
            throw new NotNullableException("workspaceId");
        }

        return this.repository.findIdByUuidAndWorkspaceId(uuid, workspaceId)
                .orElseThrow(() -> new CategoryNotFoundException(uuid));
    }
}
