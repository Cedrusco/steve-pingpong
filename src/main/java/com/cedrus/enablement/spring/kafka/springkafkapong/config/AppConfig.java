package com.cedrus.enablement.spring.kafka.springkafkapong.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app")

public class AppConfig {
  private int maxDelaySeconds;
  private int minDelaySeconds;
}
