package ru.ilezzov.group.flowgoods.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Marketplace;

import java.util.Optional;
import java.util.UUID;

public interface MarketplaceRepository extends JpaRepository<Marketplace, Long>, JpaSpecificationExecutor<Marketplace> {
    Optional<Marketplace> findByUuidAndWorkspaceId(UUID uuid, Long workspaceId);

    @Query("SELECT COUNT(m) > 0 FROM Marketplace m WHERE m.workspaceId = :workspaceId AND LOWER(m.name) = LOWER(:name)")
    boolean existsByNameIgnoreCaseAndWorkspaceId(@Param("name") final String name, @Param("workspaceId") final Long workspaceId);

    @Query("SELECT m.id FROM Marketplace m WHERE m.uuid = :uuid and m.workspaceId =:workspaceId")
    Optional<Long> findIdByUuidAndOwnerId(@Param("uuid") final UUID uuid, @Param("workspaceId") final Long workspaceId);
}
