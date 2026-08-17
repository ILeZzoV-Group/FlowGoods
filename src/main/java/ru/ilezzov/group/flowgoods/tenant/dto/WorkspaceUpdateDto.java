package ru.ilezzov.group.flowgoods.tenant.dto;

import jakarta.validation.constraints.Size;
import ru.ilezzov.group.flowgoods.common.annotation.OptionalNotBlank;
import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record WorkspaceUpdateDto (
        @Size(min = 3, max = 63, message = "{validation.workspace.name.size}")
        @OptionalNotBlank(message = "{validation.workspace.name.optional_not_blank}")
        @Trimmed
        String name
) {}
