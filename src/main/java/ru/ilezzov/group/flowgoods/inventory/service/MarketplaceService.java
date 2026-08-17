package ru.ilezzov.group.flowgoods.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Marketplace;
import ru.ilezzov.group.flowgoods.inventory.exception.marketplace.MarketplaceAlreadyExistsException;
import ru.ilezzov.group.flowgoods.inventory.exception.marketplace.MarketplaceNotFoundException;
import ru.ilezzov.group.flowgoods.inventory.mapper.MarketplaceMapper;
import ru.ilezzov.group.flowgoods.inventory.repository.MarketplaceRepository;
import ru.ilezzov.group.flowgoods.inventory.resolver.MarketplaceResolver;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MarketplaceService {
    private final MarketplaceRepository marketplaceRepository;
    private final MarketplaceResolver marketplaceResolver;
    private final MarketplaceMapper marketplaceMapper;

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
}
