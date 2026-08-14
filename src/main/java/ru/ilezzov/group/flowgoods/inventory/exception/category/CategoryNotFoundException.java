package ru.ilezzov.group.flowgoods.inventory.exception.category;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class CategoryNotFoundException extends BusinessException {
    public CategoryNotFoundException(final UUID uuid) {
        super("category-not-found", uuid);
    }
}