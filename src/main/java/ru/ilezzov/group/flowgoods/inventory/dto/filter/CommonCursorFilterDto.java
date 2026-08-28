package ru.ilezzov.group.flowgoods.inventory.dto.filter;

import ru.ilezzov.group.flowgoods.common.annotation.Trimmed;

public record CommonCursorFilterDto (
        @Trimmed
        String name,

        String cursor,

        Integer limit
){
    public CommonCursorFilterDto {
        if (limit == null) {
            limit = 20;
        }

        if (limit > 100) {
            limit = 100;
        }
    }
}