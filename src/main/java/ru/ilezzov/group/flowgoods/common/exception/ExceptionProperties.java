package ru.ilezzov.group.flowgoods.common.exception;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "app.exceptions")
public record ExceptionProperties(Map<String, ErrorConfig> errors) {
    public record ErrorConfig(
            int status,
            String title,
            String type,
            String message
    ) {}
}