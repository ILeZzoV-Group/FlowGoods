package ru.ilezzov.group.flowgoods.finance.resolver;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Context;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.general.NotNullableException;
import ru.ilezzov.group.flowgoods.finance.entity.Transaction;
import ru.ilezzov.group.flowgoods.finance.exception.TransactionNotFoundException;
import ru.ilezzov.group.flowgoods.finance.repository.TransactionRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionResolver {
    private final TransactionRepository repository;

    public Transaction resolveByUuidAndWorkspaceId(final UUID uuid, @Context final Long workspaceId) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        if (workspaceId == null) {
            throw new NotNullableException("workspaceId");
        }

        return this.repository.findByUuidAndWorkspaceId(uuid, workspaceId)
                .orElseThrow(() -> new TransactionNotFoundException(uuid));
    }
}
