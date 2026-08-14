package ru.ilezzov.group.flowgoods.inventory.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.ilezzov.group.flowgoods.common.exception.general.NotNullableException;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier;
import ru.ilezzov.group.flowgoods.inventory.exception.supplier.SupplierNotFoundException;
import ru.ilezzov.group.flowgoods.inventory.repository.SupplierRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SupplierResolver {
    private final SupplierRepository repository;

    public Supplier resolveByUuidAndWorkspaceId(final UUID uuid, final Long workspaceId) {
        if (uuid == null) {
            throw new NotNullableException("uuid");
        }

        if (workspaceId == null) {
            throw new NotNullableException("workspaceId");
        }

        return this.repository.findByUuidAndWorkspaceId(uuid, workspaceId)
                .orElseThrow(() -> new SupplierNotFoundException(uuid));
    }
}
