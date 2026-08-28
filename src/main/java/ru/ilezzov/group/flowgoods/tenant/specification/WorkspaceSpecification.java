package ru.ilezzov.group.flowgoods.tenant.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.ilezzov.group.flowgoods.tenant.entity.Workspace;
import ru.ilezzov.group.flowgoods.tenant.entity.Workspace_;

public class WorkspaceSpecification {
    public static Specification<Workspace> workspaceLikeName(final String name) {
        return (root, query, cb) -> {
            if (!StringUtils.hasText(name)) {
                return null;
            }
            return cb.like(cb.lower(root.get(Workspace_.NAME)), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Workspace> ownerIdEquals(final Long ownerId) {
        return (root, query, cb) -> cb.equal(root.get(Workspace_.ownerId), ownerId);
    }

    public static Specification<Workspace> idGreaterThan(final Long lastId) {
        return (root, query, cb) -> {
            if (lastId == null) {
                return null;
            }
            return cb.greaterThan(root.get(Workspace_.ID), lastId);
        };
    }
}
