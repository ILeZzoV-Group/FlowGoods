package ru.ilezzov.group.flowgoods.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilezzov.group.flowgoods.inventory.entity.stock.Stock;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Stock s SET s.quantity = s.quantity - :amount WHERE s.product.id = :productId AND s.workspaceId = :workspaceId AND s.quantity >= :amount")
    int deductStock(@Param("productId") final Long productId, @Param("workspaceId") final Long workspaceId, @Param("amount") final Integer amount);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE Stock s SET s.quantity = s.quantity + :amount WHERE s.product.id = :productId AND s.workspaceId = :workspaceId")
    int addStock(@Param("productId") final Long productId, @Param("workspaceId") final Long workspaceId, @Param("amount") final Integer amount);

    @Query("SELECT s.quantity FROM Stock s WHERE s.product.id = :productId AND s.workspaceId = :workspaceId")
    Optional<Integer> findQuantityByProductIdAndWorkspaceId(@Param("productId") Long productId, @Param("workspaceId") Long workspaceId);
}

