package ru.ilezzov.group.flowgoods.tenant.dto;

import jakarta.validation.constraints.Size;
import ru.ilezzov.group.flowgoods.common.annotation.NotOfSpaces;

public record WorkspaceUpdateDto (
        @Size(min = 3, max = 63, message = "{validation.workspace.name.size}")
        @NotOfSpaces(message = "{validation.workspace.name.not_of_spaces}")
        String name
) {}
