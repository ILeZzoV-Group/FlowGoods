package ru.ilezzov.group.flowgoods.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
    Optional<Category> findByUuidAndWorkspaceId(final UUID uuid, final Long workspaceId);

    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.workspaceId = :workspaceId AND LOWER(c.name) = LOWER(:name)")
    boolean existsByNameIgnoreCaseAndWorkspaceId(@Param("name") final String name, @Param("workspaceId") final Long workspaceId);

    @Query("SELECT c.id FROM Category c WHERE c.uuid = :uuid and c.workspaceId =:workspaceId")
    Optional<Long> findIdByUuidAndOwnerId(@Param("uuid") final UUID uuid, @Param("workspaceId") final Long workspaceId);
}
