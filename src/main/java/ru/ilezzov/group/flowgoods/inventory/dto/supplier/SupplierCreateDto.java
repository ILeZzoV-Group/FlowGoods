package ru.ilezzov.group.flowgoods.inventory.dto.supplier;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record SupplierCreateDto (
        @NotBlank(message = "{validation.supplier.name.not_blank}")
        @Size(min = 3, max = 63, message = "{validation.supplier.name.size}")
        @Trimmed
        String name,

        @Pattern(regexp = "^(\\+?[1-9][0-9]{7,14})?$", message = "{validation.supplier.phone.invalid}")
        @Size(max = 15, message = "{validation.supplier.phone.size}")
        String phone,

        @Email(message = "{validation.user.email.invalid}")
        @Size(max = 255, message = "{validation.user.email.size}")
        String email,

        @URL(message = "{validation.supplier.link.invalid}")
        @Size(max = 255, message = "{validation.supplier.link.size}")
        String link
) {}
