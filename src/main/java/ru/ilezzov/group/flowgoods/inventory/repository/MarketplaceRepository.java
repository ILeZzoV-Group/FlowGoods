package ru.ilezzov.group.flowgoods.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Marketplace;

import java.util.Optional;
import java.util.UUID;

public interface MarketplaceRepository extends JpaRepository<Marketplace, Long> {
    Optional<Marketplace> findByUuidAndWorkspaceId(UUID uuid, Long workspaceId);

    boolean existsByNameAndWorkspaceId(final String name, final Long workspaceId);
}
