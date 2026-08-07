package ru.ilezzov.group.flowgoods.iam.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.jwt")
public record JwtProperties (
        String type,
        String secret,
        long expiration
) {
}
