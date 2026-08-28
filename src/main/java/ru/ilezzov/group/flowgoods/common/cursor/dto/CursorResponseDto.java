package ru.ilezzov.group.flowgoods.common.cursor.dto;

import java.util.List;

public record CursorResponseDto<T> (
        List<T> data,
        String nextCursor
) {}
