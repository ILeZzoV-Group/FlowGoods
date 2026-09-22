package ru.ilezzov.group.flowgoods.inventory.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.ilezzov.group.flowgoods.inventory.entity.product.*;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier_;

public class ProductSpecification {
    public static Specification<Product> productLikeName(final String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return cb.like(cb.lower(root.get(Product_.NAME)), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Product> productLikeSku(final String sku) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(sku)) {
                return null;
            }
            return cb.like(cb.lower(root.get(Product_.SKU)), "%" + sku.toLowerCase() + "%");
        };
    }

    public static Specification<Product> statusEquals(final ProductStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }

            return cb.equal(root.get(Product_.STATUS), status);
        };
    }

    public static Specification<Product> categoryIdEquals(final Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) {
                return null;
            }
            return cb.equal(root.get(Product_.category).get(Category_.id), categoryId);
        };
    }

    public static Specification<Product> marketplaceIdEquals(final Long marketplaceId) {
        return (root, query, cb) -> {
            if (marketplaceId == null) {
                return null;
            }

            return cb.equal(root.get(Product_.marketplace).get(Marketplace_.ID), marketplaceId);
        };
    }

    public static Specification<Product> supplierIdEquals(final Long supplierId) {
        return (root, query, cb) -> {
            if (supplierId == null) {
                return null;
            }

            return cb.equal(root.get(Product_.supplier).get(Supplier_.ID), supplierId);
        };
    }

    public static Specification<Product> workspaceIdEquals(final Long workspaceId) {
        return (root, query, cb) -> cb.equal(root.get(Product_.workspaceId), workspaceId);
    }

    public static Specification<Product> idGreaterThan(final Long lastId) {
        return (root, query, cb) -> {
            if (lastId == null) {
                return null;
            }
            return cb.greaterThan(root.get(Product_.ID), lastId);
        };
    }
}
