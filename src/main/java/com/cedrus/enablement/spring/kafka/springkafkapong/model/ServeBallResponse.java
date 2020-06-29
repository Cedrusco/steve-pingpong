package com.cedrus.enablement.spring.kafka.springkafkapong.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;



@Data
public class ServeBallResponse {
  @JsonProperty
  private ApiResponse
      apiResponse;

  public ServeBallResponse(
      boolean
          successInd) {
    if (successInd) {
      this.apiResponse = new ApiResponse();
      this.apiResponse.setCode("0");
      this.apiResponse.setMessage("Success");
      this.apiResponse.setSuccessInd(true);
    } else {
      this.apiResponse = new ApiResponse();
      this.apiResponse.setCode("-1");
      this.apiResponse.setMessage("Failure");
      this.apiResponse.setSuccessInd(false);
    }
  }

  public ServeBallResponse(boolean successInd, String message, String code) {
    this.apiResponse = new ApiResponse();
    this.apiResponse.setCode(code);
    this.apiResponse.setMessage(message);
    this.apiResponse.setSuccessInd(successInd);
  }
}
