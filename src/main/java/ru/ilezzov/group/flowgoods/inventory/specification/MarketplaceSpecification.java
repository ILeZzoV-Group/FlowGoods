package ru.ilezzov.group.flowgoods.inventory.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Marketplace;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Marketplace_;

public class MarketplaceSpecification {
    public static Specification<Marketplace> marketplaceLikeName(final String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return cb.like(cb.lower(root.get(Marketplace_.NAME)), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Marketplace> workspaceIdEquals(final Long workspaceId) {
        return (root, query, cb) -> cb.equal(root.get(Marketplace_.workspaceId), workspaceId);
    }

    public static Specification<Marketplace> idGreaterThan(final Long lastId) {
        return (root, query, cb) -> {
            if (lastId == null) {
                return null;
            }
            return cb.greaterThan(root.get(Marketplace_.ID), lastId);
        };
    }
}
