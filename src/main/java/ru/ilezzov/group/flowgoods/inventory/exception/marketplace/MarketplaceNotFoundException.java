package ru.ilezzov.group.flowgoods.inventory.exception.marketplace;

import ru.ilezzov.group.flowgoods.common.exception.BusinessException;

import java.util.UUID;

public class MarketplaceNotFoundException extends BusinessException {
    public MarketplaceNotFoundException(final UUID uuid) {
        super("marketplace-not-found", uuid);
    }
}