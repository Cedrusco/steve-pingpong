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
public class PengService {
  private final TopologyProvider springPongTopology;
  private final KafkaConfig kafkaConfig;

  @Autowired
  public PengService(TopologyProvider springPongTopology, KafkaConfig kafkaConfig) {
    this.springPongTopology = springPongTopology;
    this.kafkaConfig = kafkaConfig;
  }

  public final void startPengStream() {
    log.info("Starting Peng stream...");
    final Properties pengStreamConfiguration = new Properties();

    pengStreamConfiguration.put(
        StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + PENG);
    pengStreamConfiguration.put(
        StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

    final KafkaStreams pongStream =
        new KafkaStreams(springPongTopology.getTopology(PENG), pengStreamConfiguration);

    pongStream.start();
  }
}
