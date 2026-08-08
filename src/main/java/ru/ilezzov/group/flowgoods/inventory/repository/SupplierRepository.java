package ru.ilezzov.group.flowgoods.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier;

import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findByUuidAndWorkspaceId(UUID uuid, Long workspaceId);
}
