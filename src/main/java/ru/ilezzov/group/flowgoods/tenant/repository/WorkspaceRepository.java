package ru.ilezzov.group.flowgoods.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilezzov.group.flowgoods.tenant.entity.Workspace;

import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Optional<Workspace> findByUuid(final UUID uuid);

    Optional<Workspace> findByUuidAndOwnerId(final UUID uuid, final Long ownerId);

    @Query("SELECT w.id FROM Workspace w WHERE w.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") final UUID uuid);

    @Query("SELECT w.id FROM Workspace w WHERE w.uuid = :uuid and w.ownerId =:ownerId")
    Optional<Long> findIdByUuidAndOwnerId(@Param("uuid") final UUID uuid, @Param("ownerId") final Long ownerId);

    boolean existsByNameAndOwnerId(final String name, final Long ownerId);
}
