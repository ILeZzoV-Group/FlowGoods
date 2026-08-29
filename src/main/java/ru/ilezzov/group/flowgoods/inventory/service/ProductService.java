package ru.ilezzov.group.flowgoods.inventory.service;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXParseException;
import ru.ilezzov.group.flowgoods.common.cursor.dto.CursorResponseDto;
import ru.ilezzov.group.flowgoods.common.cursor.encoder.AesCursorEncoder;
import ru.ilezzov.group.flowgoods.common.cursor.encoder.CursorEncoder;
import ru.ilezzov.group.flowgoods.inventory.dto.filter.ProductFilterDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductCreateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductResponseDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.ProductUpdateDto;
import ru.ilezzov.group.flowgoods.inventory.dto.product.marketplace.MarketplaceResponseDto;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Category;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Marketplace;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Product;
import ru.ilezzov.group.flowgoods.inventory.entity.product.ProductStatus;
import ru.ilezzov.group.flowgoods.inventory.exception.product.CannotUpdateArchivedProductException;
import ru.ilezzov.group.flowgoods.inventory.exception.product.ProductAlreadyExistsException;
import ru.ilezzov.group.flowgoods.inventory.mapper.ProductMapper;
import ru.ilezzov.group.flowgoods.inventory.repository.MarketplaceRepository;
import ru.ilezzov.group.flowgoods.inventory.repository.ProductRepository;
import ru.ilezzov.group.flowgoods.inventory.resolver.CategoryResolver;
import ru.ilezzov.group.flowgoods.inventory.resolver.MarketplaceResolver;
import ru.ilezzov.group.flowgoods.inventory.resolver.ProductResolver;
import ru.ilezzov.group.flowgoods.inventory.resolver.SupplierResolver;
import ru.ilezzov.group.flowgoods.inventory.specification.ProductSpecification;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductResolver productResolver;
    private final ProductMapper productMapper;

    private final AesCursorEncoder cursorEncoder;

    private final CategoryResolver categoryResolver;
    private final MarketplaceResolver marketplaceResolver;
    private final SupplierResolver supplierResolver;

    @Transactional(readOnly = true)
    public ProductResponseDto getProduct(final UUID uuid, final Long workspaceId) {
        return this.productMapper.toDto(
                this.productResolver.resolverByUuidAndWorkspaceId(
                        uuid, workspaceId
                )
        );
    }

    public ProductResponseDto createProduct(final ProductCreateDto dto, final Long workspaceId) {
        if (StringUtils.isNotEmpty(dto.sku())) {
            if (this.productRepository.existsBySkuIgnoreCaseAndWorkspaceId(dto.sku(), workspaceId)) {
                throw new ProductAlreadyExistsException(dto.sku());
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

        if (StringUtils.isNotEmpty(dto.sku()) && !dto.sku().equalsIgnoreCase(product.getSku())) {
            if (this.productRepository.existsBySkuIgnoreCaseAndWorkspaceId(dto.sku(), workspaceId)) {
                throw new ProductAlreadyExistsException(dto.sku());
            }
        }

        this.productMapper.updateEntity(dto, product, workspaceId);

        if (product.getStatus() == ProductStatus.ACTIVE) {
            product.validateUpdate();
        } else if (dto.publishImmediately()) {
            product.publish();
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

    public CursorResponseDto<ProductResponseDto> getProducts(final Long workspaceId, final ProductFilterDto dto) {
        final Long lastId = this.cursorEncoder.decode(dto.cursor());
        final int limit = dto.limit();

        Long categoryId = null;
        if (dto.categoryId() != null) {
            categoryId = this.categoryResolver.resolveIdByUuidAndWorkspaceId(dto.categoryId(), workspaceId);
        }
        Long marketplaceId = null;
        if (dto.marketplaceId() != null) {
            marketplaceId = this.marketplaceResolver.resolveIdByUuidAndWorkspaceId(dto.marketplaceId(), workspaceId);
        }

        Long supplierId = null;
        if (dto.supplierId() != null) {
            supplierId = this.supplierResolver.resolveIdByUuidAndWorkspaceId(dto.supplierId(), workspaceId);
        }

        final Specification<Product> specification = Specification
                .where(ProductSpecification.workspaceIdEquals(workspaceId))
                .and(ProductSpecification.categoryIdEquals(categoryId))
                .and(ProductSpecification.marketplaceIdEquals(marketplaceId))
                .and(ProductSpecification.supplierIdEquals(supplierId))
                .and(ProductSpecification.productLikeName(dto.name()))
                .and(ProductSpecification.productLikeSku(dto.sku())
                .and(ProductSpecification.statusEquals(dto.status()))
                .and(ProductSpecification.idGreaterThan(lastId)));

        final List<Product> content = productRepository.findBy(
                specification,
                query -> query
                        .sortBy(Sort.by(Sort.Direction.ASC, "id"))
                        .limit(limit + 1)
                        .all()
        );
        final boolean hasNext = content.size() > limit;

        final List<ProductResponseDto> productResponseDtoList = content.stream()
                .limit(limit)
                .map(this.productMapper::toDto)
                .toList();

        String nextCursor = null;

        if (hasNext) {
            content.removeLast();

            if (!productResponseDtoList.isEmpty()) {
                nextCursor = this.cursorEncoder.encode(content.getLast().getId());
            }
        }

        return new CursorResponseDto<>(productResponseDtoList, nextCursor);
    }
}
