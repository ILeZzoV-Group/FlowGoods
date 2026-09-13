package ru.ilezzov.group.flowgoods.finance.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.ilezzov.group.flowgoods.finance.entity.Transaction;
import ru.ilezzov.group.flowgoods.finance.entity.TransactionType;
import ru.ilezzov.group.flowgoods.finance.entity.Transaction_;

import java.time.Instant;

public class TransactionSpecification {
    public static Specification<Transaction> transactionTypeEquals(final TransactionType type) {
        return (root, query, cb) -> {
            if (type == null) {
                return null;
            }
            return cb.equal(root.get(Transaction_.type), type);
        };
    }

    public static Specification<Transaction> categoryEquals(final Long categoryId) {
        return (root, query, cb) -> {
            if (categoryId == null) {
                return null;
            }
            return cb.equal(root.get(Transaction_.category), categoryId);
        };
    }

    public static Specification<Transaction> dateGreaterThanOrEqualTo(final Instant from) {
        return (root, query, cb) -> {
            if (from == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get(Transaction_.createdAt), from);
        };
    }

    public static Specification<Transaction> dateLessThanOrEqualTo(final Instant to) {
        return (root, query, cb) -> {
            if (to == null) {
                return null;
            }
            return cb.lessThanOrEqualTo(root.get(Transaction_.createdAt), to);
        };
    }
    public static Specification<Transaction> workspaceIdEquals(final Long workspaceId) {
       return (root, query, cb) -> cb.equal(root.get(Transaction_.workspaceId), workspaceId);
    }

    public static Specification<Transaction> idLessThan(final Long lastId) {
        return (root, query, cb) -> {
            if (lastId == null) {
                return null;
            }
            return cb.lessThan(root.get(Transaction_.ID), lastId);
        };
    }
}
