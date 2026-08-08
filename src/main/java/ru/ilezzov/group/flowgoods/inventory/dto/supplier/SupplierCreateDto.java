package ru.ilezzov.group.flowgoods.inventory.dto.supplier;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

import java.util.UUID;

public record SupplierCreateDto (
        @NotBlank
        @Size(min = 3, max = 63)
        String name,

        @Pattern(regexp = "^\\+?[1-9][0-9]{7,14}$", message = "Invalid phone number format")
        @Size(max = 15)
        String phone,

        @Email
        @Size(max = 255)
        String email,

        @URL
        @Size(max = 255)
        String link
) { }
