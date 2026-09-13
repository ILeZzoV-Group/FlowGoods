package ru.ilezzov.group.flowgoods.inventory.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.general.NotNullableException;
import ru.ilezzov.group.flowgoods.inventory.exception.stock.NotEnoughStockException;
import ru.ilezzov.group.flowgoods.inventory.repository.StockRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StockResolver {
    private final StockRepository repository;

    public int resolveQuantityByProductIdAndWorkspaceId(final Long productId, final Long workspaceId, final UUID productUuid) {
        if (productId == null) {
            throw new NotNullableException("productId");
        }

        if (workspaceId == null) {
            throw new NotNullableException("workspaceId");
        }

        return this.repository.findQuantityByProductIdAndWorkspaceId(productId, workspaceId)
                .orElseThrow(() -> new NotEnoughStockException(productUuid));
    }
}
