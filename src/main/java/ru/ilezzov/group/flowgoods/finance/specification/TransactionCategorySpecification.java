package ru.ilezzov.group.flowgoods.finance.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionCategory;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionCategory_;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionType;

public class TransactionCategorySpecification {
    public static Specification<TransactionCategory> categoryLikeName(final String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return cb.like(cb.lower(root.get(TransactionCategory_.NAME)), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<TransactionCategory> categoryTypeEquals(final TransactionType type) {
        return (root, query, cb) -> {
            if (type == null) {
                return null;
            }
            return cb.equal(root.get(TransactionCategory_.type), type);
        };
    }
    public static Specification<TransactionCategory> workspaceIdEquals(final Long workspaceId) {
       return (root, query, cb) -> cb.equal(root.get(TransactionCategory_.workspaceId), workspaceId);
    }

    public static Specification<TransactionCategory> idGreaterThan(final Long lastId) {
        return (root, query, cb) -> {
            if (lastId == null) {
                return null;
            }
            return cb.greaterThan(root.get(TransactionCategory_.ID), lastId);
        };
    }
}
