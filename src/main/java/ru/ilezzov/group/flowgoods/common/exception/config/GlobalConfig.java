package ru.ilezzov.group.flowgoods.common.exception.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.ilezzov.group.flowgoods.common.exception.ExceptionProperties;

@Configuration
@EnableConfigurationProperties(ExceptionProperties.class)
public class GlobalConfig {
}
