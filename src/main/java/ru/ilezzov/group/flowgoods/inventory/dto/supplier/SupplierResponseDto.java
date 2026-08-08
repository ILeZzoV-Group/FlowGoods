package ru.ilezzov.group.flowgoods.inventory.dto.supplier;

import java.time.Instant;
import java.util.UUID;

public record SupplierResponseDto (
    UUID uuid,
    String name,
    String phone,
    String email,
    String link,
    Instant createdAt,
    Instant updatedAt
) { }