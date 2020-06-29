package com.cedrus.enablement.spring.kafka.springkafkapong.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
  @JsonProperty("successInd")
  private boolean
      successInd;

  @JsonProperty
  private String message;

  @JsonProperty private String code;
}
