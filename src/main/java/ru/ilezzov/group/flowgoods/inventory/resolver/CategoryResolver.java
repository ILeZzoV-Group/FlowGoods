package ru.ilezzov.group.flowgoods.inventory.resolver;


import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.general.NotNullableException;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Category;
import ru.ilezzov.group.flowgoods.inventory.exception.category.CategoryNotFoundException;
import ru.ilezzov.group.flowgoods.inventory.repository.CategoryRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CategoryResolver {
    private final CategoryRepository repository;

    public Category resolveByUuidAndWorkspaceId(final UUID uuid, @Context final Long workspaceId) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        if (workspaceId == null) {
            throw new NotNullableException("workspaceId");
        }

        return this.repository.findByUuidAndWorkspaceId(uuid, workspaceId)
                .orElseThrow(() -> new CategoryNotFoundException(uuid));
    }
}
