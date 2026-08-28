package ru.ilezzov.group.flowgoods.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.common.cursor.dto.CursorResponseDto;
import ru.ilezzov.group.flowgoods.common.cursor.encoder.AesCursorEncoder;
import ru.ilezzov.group.flowgoods.inventory.dto.filter.CommonCursorFilterDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.supplier.SupplierUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier;
import ru.ilezzov.group.flowgoods.inventory.mapper.SupplierMapper;
import ru.ilezzov.group.flowgoods.inventory.repository.SupplierRepository;
import ru.ilezzov.group.flowgoods.inventory.resolver.SupplierResolver;
import ru.ilezzov.group.flowgoods.inventory.specification.SupplierSpecification;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final SupplierResolver supplierResolver;

    private final AesCursorEncoder cursorEncoder;

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

    @Transactional(readOnly = true)
    public CursorResponseDto<SupplierResponseDto> getSuppliers(final Long workspaceId, final CommonCursorFilterDto dto) {
        final Long lastId = this.cursorEncoder.decode(dto.cursor());
        final int limit = dto.limit();

        final Specification<Supplier> specification = Specification
                .where(SupplierSpecification.workspaceIdEquals(workspaceId))
                .and(SupplierSpecification.supplierLikeName(dto.name()))
                .and(SupplierSpecification.idGreaterThan(lastId));

        final List<Supplier> content = supplierRepository.findBy(
                specification,
                query -> query
                        .sortBy(Sort.by(Sort.Direction.ASC, "id"))
                        .limit(limit + 1)
                        .all()
        );
        final boolean hasNext = content.size() > limit;

        final List<SupplierResponseDto> supplierResponseDtoList = content.stream()
                .limit(limit)
                .map(this.supplierMapper::toDto)
                .toList();

        String nextCursor = null;

        if (hasNext) {
            content.removeLast();

            if (!supplierResponseDtoList.isEmpty()) {
                nextCursor = this.cursorEncoder.encode(content.getLast().getId());
            }
        }

        return new CursorResponseDto<>(supplierResponseDtoList, nextCursor);
    }
}
