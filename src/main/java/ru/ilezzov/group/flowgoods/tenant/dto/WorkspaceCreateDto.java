package ru.ilezzov.group.flowgoods.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record WorkspaceCreateDto (
        @NotBlank
        @Size(min = 3, max = 63)
        String name
) {
}
