package ru.ilezzov.group.flowgoods.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.ilezzov.group.flowgoods.common.exception.ExceptionProperties;

@Configuration
@EnableConfigurationProperties(ExceptionProperties.class)
public class GlobalConfig {
}
