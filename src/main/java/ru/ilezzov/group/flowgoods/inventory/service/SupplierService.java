package ru.ilezzov.group.flowgoods.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier;
import ru.ilezzov.group.flowgoods.inventory.mapper.SupplierMapper;
import ru.ilezzov.group.flowgoods.inventory.repository.SupplierRepository;
import ru.ilezzov.group.flowgoods.inventory.resolver.SupplierResolver;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final SupplierResolver supplierResolver;

    @Transactional(readOnly = true)
    public SupplierResponseDto getSupplier(final UUID uuid, final Long workspaceId) {
        return this.supplierMapper.toDto(
                this.supplierResolver.resolveByUuidAndWorkspaceId(
                        uuid, workspaceId
                )
        );
    }

    public SupplierResponseDto createSupplier(final SupplierCreateDto dto, final Long workspaceId) {
        final Supplier supplier = this.supplierMapper.toEntity(dto, workspaceId);
        return this.supplierMapper.toDto(
                this.supplierRepository.save(supplier)
        );
    }

    public SupplierResponseDto updateSupplier(final UUID uuid, final SupplierUpdateDto dto, final Long workspaceId) {
        final Supplier supplier = this.supplierResolver.resolveByUuidAndWorkspaceId(uuid, workspaceId);
        this.supplierMapper.updateEntity(dto, supplier);
        return this.supplierMapper.toDto(supplier);
    }
}
