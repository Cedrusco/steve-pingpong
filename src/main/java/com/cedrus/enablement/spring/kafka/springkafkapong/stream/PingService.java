package com.cedrus.enablement.spring.kafka.springkafkapong.stream;

import com.cedrus.enablement.spring.kafka.springkafkapong.config.KafkaConfig;
import com.cedrus.enablement.spring.kafka.springkafkapong.model.PongTarget;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import static com.cedrus.enablement.spring.kafka.springkafkapong.model.PongTarget.*;

@Slf4j
@Service
public class PingService {
  private final TopologyProvider springPongTopology;
  private final KafkaConfig kafkaConfig;

  @Autowired
  public PingService(TopologyProvider springPongTopology, KafkaConfig kafkaConfig) {
    this.springPongTopology = springPongTopology;
    this.kafkaConfig = kafkaConfig;
  }

  public final void startPingStream() {
    log.info("Starting Ping stream...");
    final Properties pingStreamConfiguration = new Properties();

    pingStreamConfiguration.put(
        StreamsConfig.APPLICATION_ID_CONFIG, kafkaConfig.getKafkaAppId() + PING);
    pingStreamConfiguration.put(
        StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfig.getBootstrapServers());

    final KafkaStreams pingStream =
        new KafkaStreams(springPongTopology.getTopology(PING), pingStreamConfiguration);

    pingStream.start();
  }
}

