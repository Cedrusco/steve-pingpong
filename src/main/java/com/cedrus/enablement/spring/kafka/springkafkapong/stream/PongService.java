package com.cedrus.enablement.spring.kafka.springkafkapong.stream;

import com.cedrus.enablement.spring.kafka.springkafkapong.config.KafkaConfig;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.cedrus.enablement.spring.kafka.springkafkapong.model.PongTarget.*;

@Slf4j
@Service
public class PongService {
  private final TopologyProvider springPongTopology;
  private final KafkaConfig kafkaConfig;

  @Autowired
  public PongService(TopologyProvider springPongTopology, KafkaConfig kafkaConfig) {
    this.springPongTopology = springPongTopology;
    this.kafkaConfig = kafkaConfig;
  }

  public final void startPongStream() {
    log.info("Starting Pong stream...");
    final Properties pongStreamConfiguration = new Properties();

    pongStreamConfiguration.put(
        StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + PONG);
    pongStreamConfiguration.put(
        StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

    final KafkaStreams pongStream =
        new KafkaStreams(springPongTopology.getTopology(PONG), pongStreamConfiguration);

    pongStream.start();
  }
}
