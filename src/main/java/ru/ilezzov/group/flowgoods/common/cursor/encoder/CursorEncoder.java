package ru.ilezzov.group.flowgoods.common.cursor.encoder;

public interface CursorEncoder {
    String encode(final Long id);

    Long decode(final String cursor);
}
