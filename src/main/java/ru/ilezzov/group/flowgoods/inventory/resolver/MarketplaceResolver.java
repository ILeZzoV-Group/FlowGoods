package ru.ilezzov.group.flowgoods.inventory.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.general.NotNullableException;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Marketplace;
import ru.ilezzov.group.flowgoods.inventory.exception.marketplace.MarketplaceNotFoundException;
import ru.ilezzov.group.flowgoods.inventory.repository.MarketplaceRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MarketplaceResolver {
    private final MarketplaceRepository repository;

    public Marketplace resolverByUuidAndWorkspace(final UUID uuid, final Long workspaceId) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        if (workspaceId == null) {
            throw new NotNullableException("workspaceId");
        }

        return this.repository.findByUuidAndWorkspaceId(uuid, workspaceId)
                .orElseThrow(() -> new MarketplaceNotFoundException(uuid, workspaceId));
    }
}
