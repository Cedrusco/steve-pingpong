package com.cedrus.enablement.spring.kafka.springkafkapong.stream;

import com.cedrus.enablement.spring.kafka.springkafkapong.kafka.PongBallProducer;
import com.cedrus.enablement.spring.kafka.springkafkapong.model.PongBall;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class AddBallService {
  private final PongBallProducer pongBallProducer;
  private final ObjectMapper objectMapper;

  public AddBallService(PongBallProducer pongBallProducer, ObjectMapper objectMapper) {
    this.pongBallProducer = pongBallProducer;
    this.objectMapper = objectMapper;
  }

  public void addBall(PongBall pongBall) {
    try {
      String ballAsJson = objectMapper.writeValueAsString(pongBall);
      pongBallProducer.sendMessage(ballAsJson);
    } catch (Exception ex) {
      throw new RuntimeException(ex);
    }
  }
}
