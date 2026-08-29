package ru.ilezzov.group.flowgoods.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier;

import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
    Optional<Supplier> findByUuidAndWorkspaceId(UUID uuid, Long workspaceId);

    @Query("SELECT s.id FROM Supplier s WHERE s.uuid = :uuid and s.workspaceId =:workspaceId")
    Optional<Long> findIdByUuidAndOwnerId(@Param("uuid") final UUID uuid, @Param("workspaceId") final Long workspaceId);
}
