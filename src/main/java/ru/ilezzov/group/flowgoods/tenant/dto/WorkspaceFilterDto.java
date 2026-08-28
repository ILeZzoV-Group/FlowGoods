package ru.ilezzov.group.flowgoods.tenant.dto;

import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record WorkspaceFilterDto(
        @Trimmed
        String name
) {}
