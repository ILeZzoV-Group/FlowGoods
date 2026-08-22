package ru.ilezzov.group.flowgoods.inventory.entity.product;


import jakarta.persistence.*;
import lombok.*;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier;
import ru.ilezzov.group.flowgoods.inventory.entity.WorkspaceEntity;
import ru.ilezzov.group.flowgoods.inventory.exception.product.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends WorkspaceEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "sku", length = 63)
    private String sku;

    @Column(name = "status", length = 63, nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.DRAFT;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marketplace_id")
    private Marketplace marketplace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;

    public void publish() {
        if (this.status == ProductStatus.ARCHIVED) {
            throw new CannotPublishArchivedProductException(this.getUuid());
        }

        final List<String> requiredParams = getRequiredParams();

        if (!requiredParams.isEmpty()) {
            throw new ProductCannotBePublishedMissingFieldsException(this.getUuid(), requiredParams);
        }

        this.status = ProductStatus.ACTIVE;
    }

    public void validateUpdate() {
        if (this.status == ProductStatus.ARCHIVED) {
            throw new CannotUpdateArchivedProductException(this.getUuid());
        }

        final List<String> requiredParams = getRequiredParams();

        if (!requiredParams.isEmpty()) {
            throw new ProductCannotBeUpdatedMissingFieldsException(this.getUuid(), requiredParams);
        }
    }

    public void suspend() {
        if (this.status == ProductStatus.ARCHIVED) {
            // Тебе нужно будет создать это исключение и занести в yaml
            throw new CannotSuspendArchivedProductException(this.getUuid());
        }

        if (this.status == ProductStatus.INACTIVE) {
            return;
        }

        this.status = ProductStatus.INACTIVE;
    }

    public void archive() {
        if (this.status == ProductStatus.ARCHIVED) {
            return;
        }

        this.status = ProductStatus.ARCHIVED;
    }

    private List<String> getRequiredParams() {
        final List<String> requiredParams = new ArrayList<>();

        if (this.sku == null || this.sku.isBlank()) {
            requiredParams.add("sku");
        }

        if (this.price == null) {
            requiredParams.add("price");
        }

        if (this.category == null) {
            requiredParams.add("categoryId");
        }

        if (this.marketplace == null) {
            requiredParams.add("marketplaceId");
        }

        if (this.supplier == null) {
            requiredParams.add("supplierId");
        }
        return requiredParams;
    }
}
