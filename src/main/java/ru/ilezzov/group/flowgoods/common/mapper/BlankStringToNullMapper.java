package ru.ilezzov.group.flowgoods.common.mapper;

import org.springframework.stereotype.Component;

@Component
public class BlankStringToNullMapper {
    public String mapString(final String value) {
        if (value != null && value.isBlank()) {
            return null;
        }

        return value;
    }
}
