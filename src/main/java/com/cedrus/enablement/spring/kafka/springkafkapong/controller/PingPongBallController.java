package com.cedrus.enablement.spring.kafka.springkafkapong.controller;

import com.cedrus.enablement.spring.kafka.springkafkapong.model.ServeBallRequest;
import com.cedrus.enablement.spring.kafka.springkafkapong.model.ServeBallResponse;
import com.cedrus.enablement.spring.kafka.springkafkapong.model.Color;
import com.cedrus.enablement.spring.kafka.springkafkapong.model.PongBall;
import com.cedrus.enablement.spring.kafka.springkafkapong.model.PongTarget;
import com.cedrus.enablement.spring.kafka.springkafkapong.stream.AddBallService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PingPongBallController {

  private final AddBallService addBallService;

  @Autowired
  public PingPongBallController(AddBallService addBallService) {
    this.addBallService = addBallService;
  }

  @PostMapping(value = "/ball")
  public ResponseEntity<ServeBallResponse> serveBall(@RequestBody ServeBallRequest serveBallRequest) {
    log.info("Received request to add ball.");
    log.debug("Request = {}", serveBallRequest);

    return addBall(serveBallRequest);
  }

  private ResponseEntity<ServeBallResponse> addBall(ServeBallRequest serveBallRequest) {
    try {
      final PongBall pongBall =
          new PongBall(serveBallRequest.getId(), PongTarget.PING, Color.valueOf(serveBallRequest.getColor()));

      addBallService.addBall(pongBall);
      ServeBallResponse serveBallResponse = new ServeBallResponse(true);

      return new ResponseEntity<>(serveBallResponse, HttpStatus.OK);
    } catch (Exception e) {
      log.error("RuntimeException caught when trying to get ball.", e);
      ServeBallResponse serveBallResponse =
          new ServeBallResponse(false, e.getMessage(), "INTERNAL_ERROR");
      return new ResponseEntity<>(serveBallResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
