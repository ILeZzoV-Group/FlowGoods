package ru.ilezzov.group.flowgoods.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceResponseDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Marketplace;
import ru.ilezzov.group.flowgoods.inventory.exception.marketplace.MarketplaceAlreadyExistsException;
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
                this.marketplaceResolver.resolverByUuidAndWorkspace(
                        uuid, workspaceId
                )
        );
    }

    public MarketplaceResponseDto createMarketplace(final MarketplaceCreateDto dto, final Long workspaceId) {
        if (this.marketplaceRepository.existsByNameAndWorkspaceId(dto.name(), workspaceId)) {
            throw new MarketplaceAlreadyExistsException(dto.name());
        }

        final Marketplace marketplace = Marketplace.builder()
                .name(dto.name())
                .url(dto.url())
                .workspaceId(workspaceId)
                .build();

        return this.marketplaceMapper.toDto(
                this.marketplaceRepository.save(marketplace)
        );
    }
}
