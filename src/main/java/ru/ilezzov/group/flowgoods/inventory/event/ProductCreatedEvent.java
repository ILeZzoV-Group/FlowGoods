package ru.ilezzov.group.flowgoods.inventory.event;

import ru.ilezzov.group.flowgoods.inventory.entity.product.Product;

public record ProductCreatedEvent (
        Product product
) { }
