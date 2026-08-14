package ru.ilezzov.group.flowgoods.tenant.dto;

import jakarta.validation.constraints.Size;

public record WorkspaceUpdateDto (
        @Size(min = 3, max = 63, message = "{validation.workspace.name.size}")
        String name
) {}
