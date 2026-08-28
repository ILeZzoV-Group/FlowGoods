package ru.ilezzov.group.flowgoods.inventory.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier;
import ru.ilezzov.group.flowgoods.inventory.entity.supplier.Supplier_;

public class SupplierSpecification {
    public static Specification<Supplier> supplierLikeName(final String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return cb.like(cb.lower(root.get(Supplier_.NAME)), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Supplier> workspaceIdEquals(final Long workspaceId) {
        return (root, query, cb) -> cb.equal(root.get(Supplier_.workspaceId), workspaceId);
    }

    public static Specification<Supplier> idGreaterThan(final Long lastId) {
        return (root, query, cb) -> {
            if (lastId == null) {
                return null;
            }
            return cb.greaterThan(root.get(Supplier_.ID), lastId);
        };
    }
}
