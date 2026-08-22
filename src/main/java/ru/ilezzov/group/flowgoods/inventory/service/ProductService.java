package ru.ilezzov.group.flowgoods.inventory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Product;
import ru.ilezzov.group.flowgoods.inventory.entity.product.ProductStatus;
import ru.ilezzov.group.flowgoods.inventory.exception.product.CannotUpdateArchivedProductException;
import ru.ilezzov.group.flowgoods.inventory.exception.product.ProductAlreadyExistsException;
import ru.ilezzov.group.flowgoods.inventory.mapper.ProductMapper;
import ru.ilezzov.group.flowgoods.inventory.repository.ProductRepository;
import ru.ilezzov.group.flowgoods.inventory.resolver.ProductResolver;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductResolver productResolver;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(final UUID uuid, final Long workspaceId) {
        return this.productMapper.toDto(
                this.productResolver.resolverByUuidAndWorkspaceId(
                        uuid, workspaceId
                )
        );
    }

    public ProductResponseDto createProduct(final ProductCreateDto dto, final Long workspaceId) {
        if (dto.sku() != null) {
            if (!dto.sku().isBlank()) {
                if (this.productRepository.existsBySkuIgnoreCaseAndWorkspaceId(dto.sku(), workspaceId)) {
                    throw new ProductAlreadyExistsException(dto.sku());
                }
            }
        }

        final Product product = this.productMapper.toEntity(dto, workspaceId, workspaceId);
        if (dto.publishImmediately()) {
            product.publish();
        }

        return this.productMapper.toDto(
                productRepository.save(product)
        );
    }

    public ProductResponseDto updateProduct(final UUID uuid, final ProductUpdateDto dto, final Long workspaceId) {
        final Product product = this.productResolver.resolverByUuidAndWorkspaceId(uuid, workspaceId);

        if (product.getStatus() == ProductStatus.ARCHIVED) {
            throw new CannotUpdateArchivedProductException(uuid);
        }

        if (dto.sku() != null) {
            if (!dto.sku().isBlank()) {
                if (!product.getSku().equalsIgnoreCase(dto.sku())) {
                    if (this.productRepository.existsBySkuIgnoreCaseAndWorkspaceId(dto.sku(), workspaceId)) {
                        throw new ProductAlreadyExistsException(dto.sku());
                    }
                }
            }
        }

        this.productMapper.updateEntity(dto, product, workspaceId);

        if (dto.publishImmediately() || product.getStatus() == ProductStatus.ACTIVE) {
            product.validateUpdate();
        }

        return this.productMapper.toDto(product);
    }

    public ProductResponseDto publishProduct(final UUID uuid, final Long workspaceId) {
        final Product product = this.productResolver.resolverByUuidAndWorkspaceId(uuid, workspaceId);
        product.publish();
        return this.productMapper.toDto(product);
    }

    public ProductResponseDto suspendProduct(final UUID uuid, final Long workspaceId) {
        final Product product = this.productResolver.resolverByUuidAndWorkspaceId(uuid, workspaceId);
        product.suspend();
        return this.productMapper.toDto(product);
    }

    public ProductResponseDto archiveProduct(final UUID uuid, final Long workspaceId) {
        final Product product = this.productResolver.resolverByUuidAndWorkspaceId(uuid, workspaceId);
        product.archive();
        return this.productMapper.toDto(product);
    }
}
