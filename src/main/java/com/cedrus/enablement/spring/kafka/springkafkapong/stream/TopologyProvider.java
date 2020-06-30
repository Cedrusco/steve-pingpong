package com.cedrus.enablement.spring.kafka.springkafkapong.stream;

import com.cedrus.enablement.spring.kafka.springkafkapong.config.AppConfig;
import com.cedrus.enablement.spring.kafka.springkafkapong.config.TopicConfig;
import com.cedrus.enablement.spring.kafka.springkafkapong.model.PongBall;
import com.cedrus.enablement.spring.kafka.springkafkapong.model.PongTarget;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.ValueTransformerSupplier;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TopologyProvider {

  private final TopicConfig topicConfig;
  private final AppConfig appConfig;
  private final ObjectMapper objectMapper;

    @Autowired
    public TopologyProvider(TopicConfig topicConfig, AppConfig appConfig, ObjectMapper objectMapper){
        this.topicConfig = topicConfig;
        this.appConfig = appConfig;
        this.objectMapper = objectMapper;
    }

    public Topology getTopology(PongTarget pongTarget) {
        final StreamsBuilder builder = new StreamsBuilder();
        log.info("Stream builder initialized");
        log.info(pongTarget.toString());

        final KStream<String, String> incomingStream = builder.stream(topicConfig.getTopicName(), Consumed.with(Serdes.String(), Serdes.String()));

        final KStream<String, String> filteredStream = incomingStream.branch((key, value) -> {
            PongBall pingPongBall = deserialize(value);
            log.info("deserialize value: {}", pingPongBall);
            return pingPongBall.getPongTarget().equals(pongTarget);
        })[0];

        final KStream<String, String> loggedAndDelayedStream = filteredStream.transformValues(getLogsAndDelay());

        loggedAndDelayedStream.to(topicConfig.getTopicName(), Produced.with(Serdes.String(), Serdes.String()));

        return builder.build();
    }

    private PongBall deserialize(String pingPongBallString) {
        try {
            return objectMapper.readValue(pingPongBallString, PongBall.class);
        } catch (Exception e) {
            log.debug("Deserialize error: {}", pingPongBallString);
            throw new RuntimeException(e);
        }
    }

    private String serialize(PongBall pingPongBall) {
        try {
            return objectMapper.writeValueAsString(pingPongBall);
        } catch (Exception e) {
            log.debug("Serialize error: {}", pingPongBall);
            throw new RuntimeException(e);
        }
    }

    private ValueTransformerSupplier<String, String> getLogsAndDelay() {
        return () -> new ValueTransformer<String, String>() {
            @Override
            public void init(ProcessorContext context) {}

            @Override
            public String transform(String value) {
                log.debug("Ping pong ball received");
                log.info("Transforming ping pong ball: {}", value);
                final int minDelay = appConfig.getMinDelaySeconds();
                final int maxDelay = appConfig.getMaxDelaySeconds();

                final int sleepTime = ThreadLocalRandom.current().nextInt((maxDelay - minDelay) + minDelay);
                log.debug("Sleep for: {}", sleepTime);

                try {
                    Thread.sleep(sleepTime * 1000L);
                } catch (InterruptedException e) {
                    log.error("Sleep interrupted", e);
                }

                final PongBall pingPongBall = deserialize(value);

                log.info("Returning ping pong ball: {}", pingPongBall);
                pingPongBall.returnBall(pingPongBall);
                return serialize(pingPongBall);
            }

            @Override
            public void close() {}
        };
    }
}
