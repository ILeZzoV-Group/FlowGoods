package ru.ilezzov.group.flowgoods.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.common.cursor.dto.CursorResponseDto;
import ru.ilezzov.group.flowgoods.common.cursor.encoder.AesCursorEncoder;
import ru.ilezzov.group.flowgoods.inventory.dto.filter.CommonCursorFilterDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Marketplace;
import ru.ilezzov.group.flowgoods.inventory.exception.marketplace.MarketplaceAlreadyExistsException;
import ru.ilezzov.group.flowgoods.inventory.mapper.MarketplaceMapper;
import ru.ilezzov.group.flowgoods.inventory.repository.MarketplaceRepository;
import ru.ilezzov.group.flowgoods.inventory.resolver.MarketplaceResolver;
import ru.ilezzov.group.flowgoods.inventory.specification.MarketplaceSpecification;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MarketplaceService {
    private final MarketplaceRepository marketplaceRepository;
    private final MarketplaceResolver marketplaceResolver;
    private final MarketplaceMapper marketplaceMapper;

    private final AesCursorEncoder cursorEncoder;

    @Transactional(readOnly = true)
    public MarketplaceResponseDto getMarketplace(final UUID uuid, final Long workspaceId) {
        return this.marketplaceMapper.toDto(
                this.marketplaceResolver.resolveByUuidAndWorkspaceId(
                        uuid, workspaceId
                )
        );
    }

    public MarketplaceResponseDto createMarketplace(final MarketplaceCreateDto dto, final Long workspaceId) {
        if (this.marketplaceRepository.existsByNameIgnoreCaseAndWorkspaceId(dto.name(), workspaceId)) {
            throw new MarketplaceAlreadyExistsException(dto.name());
        }

        final Marketplace marketplace = this.marketplaceMapper.toEntity(dto, workspaceId);
        return this.marketplaceMapper.toDto(
                this.marketplaceRepository.save(marketplace)
        );
    }

    public MarketplaceResponseDto updateMarketplace(final UUID uuid, final MarketplaceUpdateDto dto, final Long workspaceId) {
        final Marketplace marketplace = this.marketplaceResolver.resolveByUuidAndWorkspaceId(uuid, workspaceId);

        if (!marketplace.getName().equalsIgnoreCase(dto.name())) {
            if (this.marketplaceRepository.existsByNameIgnoreCaseAndWorkspaceId(dto.name(), workspaceId)) {
                throw new MarketplaceAlreadyExistsException(dto.name());
            }
        }

        this.marketplaceMapper.updateEntity(dto, marketplace);
        return this.marketplaceMapper.toDto(marketplace);
    }

    @Transactional(readOnly = true)
    public CursorResponseDto<MarketplaceResponseDto> getMarketplaces(final Long workspaceId, final CommonCursorFilterDto dto) {
        final Long lastId = this.cursorEncoder.decode(dto.cursor());
        final int limit = dto.limit();

        final Specification<Marketplace> specification = Specification
                .where(MarketplaceSpecification.workspaceIdEquals(workspaceId))
                .and(MarketplaceSpecification.marketplaceLikeName(dto.name()))
                .and(MarketplaceSpecification.idGreaterThan(lastId));

        final List<Marketplace> content = marketplaceRepository.findBy(
                specification,
                query -> query
                        .sortBy(Sort.by(Sort.Direction.ASC, "id"))
                        .limit(limit + 1)
                        .all()
        );
        final boolean hasNext = content.size() > limit;

        final List<MarketplaceResponseDto> marketplaceResponseDtoList = content.stream()
                .limit(limit)
                .map(this.marketplaceMapper::toDto)
                .toList();

        String nextCursor = null;

        if (hasNext) {
            content.removeLast();

            if (!marketplaceResponseDtoList.isEmpty()) {
                nextCursor = this.cursorEncoder.encode(content.getLast().getId());
            }
        }

        return new CursorResponseDto<>(marketplaceResponseDtoList, nextCursor);
    }
}
