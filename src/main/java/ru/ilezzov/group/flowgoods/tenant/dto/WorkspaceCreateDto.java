package ru.ilezzov.group.flowgoods.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record WorkspaceCreateDto (
        @NotBlank(message = "{validation.workspace.name.not_blank}")
        @Size(min = 3, max = 63, message = "{validation.workspace.name.size}")
        @Trimmed
        String name
) {}
