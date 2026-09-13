package ru.ilezzov.group.flowgoods.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionCategory;

import java.util.Optional;
import java.util.UUID;

public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Long>, JpaSpecificationExecutor<TransactionCategory> {
    Optional<TransactionCategory> findByUuidAndWorkspaceId(final UUID uuid, final Long workspaceId);

    @Query("SELECT COUNT(t) > 0 FROM TransactionCategory t WHERE t.workspaceId = :workspaceId AND LOWER(t.name) = LOWER(:name)")
    boolean existsByNameIgnoreCaseAndWorkspaceId(@Param("name") String name, @Param("workspaceId") Long workspaceId);

    @Query("SELECT t.id FROM TransactionCategory t WHERE t.uuid = :uuid and t.workspaceId =:workspaceId")
    Optional<Long> findIdByUuidAndWorkspaceId(@Param("uuid") final UUID uuid, @Param("workspaceId") final Long workspaceId);
}
