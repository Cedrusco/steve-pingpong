package com.cedrus.enablement.spring.kafka.springkafkapong;

import com.cedrus.enablement.spring.kafka.springkafkapong.config.AppConfig;
import com.cedrus.enablement.spring.kafka.springkafkapong.config.KafkaConfig;
import com.cedrus.enablement.spring.kafka.springkafkapong.config.TopicConfig;
import com.cedrus.enablement.spring.kafka.springkafkapong.stream.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties({AppConfig.class, KafkaConfig.class, TopicConfig.class})
public class SpringKafkaPongApplication {


  public static void main(String[] args) {
    SpringApplication.run(SpringKafkaPongApplication.class, args);
  }

  @Bean
  public CommandLineRunner pingRunner(ApplicationContext context) {
    return args -> {
      ((PingService) context.getBean("pingService")).startPingStream();
    };
  }

  @Bean
  public CommandLineRunner pangRunner(ApplicationContext context) {
    return args -> {
      ((PangService) context.getBean("pangService")).startPangStream();
    };
  }

  @Bean
  public CommandLineRunner pengRunner(ApplicationContext context) {
    return args -> {
      ((PengService) context.getBean("pengService")).startPengStream();
    };
  }

  @Bean
  public CommandLineRunner pungRunner(ApplicationContext context) {
    return args -> {
      ((PungService) context.getBean("pungService")).startPungStream();
    };
  }

  @Bean
  public CommandLineRunner pongRunner(ApplicationContext context) {
    return args -> {
      ((PongService) context.getBean("pongService")).startPongStream();
    };
  }
}
