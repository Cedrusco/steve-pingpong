package com.cedrus.enablement.spring.kafka.springkafkapong.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Random;

@Data
@AllArgsConstructor
public class PongBall {
  private final String id;
  private PongTarget pongTarget;
  private final Color color;

  public void returnBall(PongBall pingPongBall) {
    PongTarget previousTarget;
    PongTarget newTarget;

    previousTarget = pingPongBall.pongTarget;
    newTarget = randomTarget();

    if(previousTarget.equals(newTarget)) {
      randomTarget();
    } else this.pongTarget = newTarget;
  }

  private PongTarget randomTarget() {

    int pick = new Random().nextInt(PongTarget.values().length);

    return PongTarget.values()[pick];

  }
}
