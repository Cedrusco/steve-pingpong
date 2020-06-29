package com.cedrus.enablement.spring.kafka.springkafkapong.kafka;

import com.cedrus.enablement.spring.kafka.springkafkapong.config.KafkaConfig;
import com.cedrus.enablement.spring.kafka.springkafkapong.config.TopicConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PongBallProducer {

  private final KafkaConfig kafkaConfig;
  private final TopicConfig topicConfig;
  private final ObjectMapper objectMapper;

  public PongBallProducer(
      KafkaConfig kafkaConfig, TopicConfig topicConfig, ObjectMapper objectMapper) {
    this.kafkaConfig = kafkaConfig;
    this.topicConfig = topicConfig;
    this.objectMapper = objectMapper;
  }

  public void sendMessage(String event) {
    log.info("Sending event={}", event);

    final Serde<String> stringSerde = Serdes.String();
    final Properties kafkaProperties = new Properties();
    kafkaProperties.put(StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId());
    kafkaProperties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());
    kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    kafkaProperties.put(
        "value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    Producer<String, String> producer = new KafkaProducer<>(kafkaProperties);

    ProducerRecord<String, String> producerRecord =
        new ProducerRecord<>(
            topicConfig.getTopicName(),
            null,
            event);
    producer.send(producerRecord);
    producer.close();
  }
}
