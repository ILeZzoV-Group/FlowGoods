package ru.ilezzov.group.flowgoods.inventory.exception.category;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class CategoryAlreadyExistsException extends BusinessException {
    public CategoryAlreadyExistsException(final String name) {
        super("category-already-exists", name);
    }
}