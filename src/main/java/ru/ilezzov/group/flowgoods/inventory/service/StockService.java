package ru.ilezzov.group.flowgoods.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.inventory.dto.stock.StockAddDeductDto;
import ru.ilezzov.group.flowgoods.inventory.dto.stock.StockResponseDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Product;
import ru.ilezzov.group.flowgoods.inventory.entity.stock.Stock;
import ru.ilezzov.group.flowgoods.inventory.event.ProductCreatedEvent;
import ru.ilezzov.group.flowgoods.inventory.exception.stock.NotEnoughStockException;
import ru.ilezzov.group.flowgoods.inventory.exception.stock.StockNotFoundException;
import ru.ilezzov.group.flowgoods.inventory.repository.StockRepository;
import ru.ilezzov.group.flowgoods.inventory.resolver.ProductResolver;
import ru.ilezzov.group.flowgoods.inventory.resolver.StockResolver;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {
    private final StockRepository stockRepository;
    private final StockResolver stockResolver;

    private final ProductResolver productResolver;

    @EventListener(classes = ProductCreatedEvent.class)
    public void handleProductCreatedEvent(final ProductCreatedEvent event) {
        final Product product = event.product();
        final Stock stock = new Stock(product, 0);

        stock.setWorkspaceId(product.getWorkspaceId());
        stockRepository.save(stock);
    }

    public StockResponseDto addStock(final StockAddDeductDto dto, final UUID productUuid, final Long workspaceId) {
        final Long productId = this.productResolver.resolverIdByUuidAndWorkspaceId(productUuid, workspaceId);
        final int status = this.stockRepository.addStock(productId, workspaceId, dto.amount());

        if (status == 0) {
            throw new StockNotFoundException(productUuid);
        }

        return new StockResponseDto(
                productUuid, this.stockResolver.resolveQuantityByProductIdAndWorkspaceId(
                        productId, workspaceId, productUuid
        )
        );
    }

    public StockResponseDto deductStock(final StockAddDeductDto dto, final UUID productUuid, final Long workspaceId) {
        final Long productId = this.productResolver.resolverIdByUuidAndWorkspaceId(productUuid, workspaceId);
        final int status = this.stockRepository.deductStock(productId, workspaceId, dto.amount());

        if (status == 0) {
            throw new NotEnoughStockException(productUuid);
        }


        return new StockResponseDto(
                productUuid, this.stockResolver.resolveQuantityByProductIdAndWorkspaceId(
                        productId, workspaceId, productUuid
        )
        );
    }
}
