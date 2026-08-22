package ru.ilezzov.group.flowgoods.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Product;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByUuidAndWorkspaceId(final UUID uuid, final Long workspaceId);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.workspaceId = :workspaceId AND LOWER(p.sku) = LOWER(:sku)")
    boolean existsBySkuIgnoreCaseAndWorkspaceId(@Param("sku") final String sku, @Param("workspaceId") final Long workspaceId);

}
