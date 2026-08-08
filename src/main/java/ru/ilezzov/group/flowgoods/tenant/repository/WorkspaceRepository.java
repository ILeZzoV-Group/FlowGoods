package ru.ilezzov.group.flowgoods.tenant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilezzov.group.flowgoods.tenant.entity.Workspace;

import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
    Optional<Workspace> findByUuid(final UUID uuid);

    @Query("SELECT w.id FROM Workspace w WHERE w.uuid = :uuid")
    Optional<Long> findIdByUuid(@Param("uuid") final UUID uuid);

    boolean existsByNameAndOwnerId(final String name, final Long ownerId);
}
