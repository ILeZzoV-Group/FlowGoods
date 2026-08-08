package ru.ilezzov.group.flowgoods.inventory.exception.marketplace;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

public class MarketplaceAlreadyExistsException extends BusinessException {
    public MarketplaceAlreadyExistsException(final String name) {
        super("marketplace-already-exists", name);
    }
}
