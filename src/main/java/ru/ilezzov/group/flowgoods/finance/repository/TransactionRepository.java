package ru.ilezzov.group.flowgoods.finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ilezzov.group.flowgoods.finance.entity.Transaction;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionCategory;

import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    Optional<Transaction> findByUuidAndWorkspaceId(final UUID uuid, final Long workspaceId);

    boolean existsByWorkspaceIdAndIdempotencyKey(final Long workspaceId, final String idempotencyKey);
}
