package ru.ilezzov.group.flowgoods.inventory.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Category;
import ru.ilezzov.group.flowgoods.inventory.entity.product.Category_;

public class CategorySpecification {
    public static Specification<Category> categoryLikeName(final String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return cb.like(cb.lower(root.get(Category_.NAME)), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Category> workspaceIdEquals(final Long workspaceId) {
        return (root, query, cb) -> cb.equal(root.get(Category_.workspaceId), workspaceId);
    }

    public static Specification<Category> idGreaterThan(final Long lastId) {
        return (root, query, cb) -> {
            if (lastId == null) {
                return null;
            }
            return cb.greaterThan(root.get(Category_.ID), lastId);
        };
    }
}
