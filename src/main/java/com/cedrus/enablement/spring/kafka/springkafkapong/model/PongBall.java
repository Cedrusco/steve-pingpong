package com.cedrus.enablement.spring.kafka.springkafkapong.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PongBall {
  private final String id;
  private PongTarget pongTarget;
  private final Color color;

  public void returnBall() {
    if (pongTarget.equals(PongTarget.PING)) {
      this.pongTarget = PongTarget.PONG;
    } else this.pongTarget = PongTarget.PING;
  }
}
