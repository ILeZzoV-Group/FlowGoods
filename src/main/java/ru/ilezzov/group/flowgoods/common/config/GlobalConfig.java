package ru.ilezzov.group.flowgoods.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.ilezzov.group.flowgoods.common.exception.ExceptionProperties;
import ru.ilezzov.group.flowgoods.iam.jwt.JwtProperties;

@Configuration
@EnableConfigurationProperties({ExceptionProperties.class, JwtProperties.class})
public class GlobalConfig {
}
