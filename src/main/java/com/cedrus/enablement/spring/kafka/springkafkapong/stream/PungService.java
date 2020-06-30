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
public class PungService {
  private final TopologyProvider springPongTopology;
  private final KafkaConfig kafkaConfig;

  @Autowired
  public PungService(TopologyProvider springPongTopology, KafkaConfig kafkaConfig) {
    this.springPongTopology = springPongTopology;
    this.kafkaConfig = kafkaConfig;
  }

  public final void startPungStream() {
    log.info("Starting Pung stream...");
    final Properties pungStreamConfiguration = new Properties();

    pungStreamConfiguration.put(
        StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + PUNG);
    pungStreamConfiguration.put(
        StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

    final KafkaStreams pongStream =
        new KafkaStreams(springPongTopology.getTopology(PUNG), pungStreamConfiguration);

    pongStream.start();
  }
}
