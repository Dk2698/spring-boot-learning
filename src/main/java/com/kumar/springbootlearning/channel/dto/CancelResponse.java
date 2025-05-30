package com.kumar.springbootlearning.channel.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CancelResponse implements Serializable {
    private String status;
    private String message;
    private Boolean success;
    private String orderNumber;

    public CancelResponse() {
    }

    public CancelResponse(String status, String message, Boolean success) {
        this.status = status;
        this.message = message;
        this.success = success;
    }

    public CancelResponse(String status, String message, Boolean success, String orderNumber) {
        this.status = status;
        this.message = message;
        this.success = success;
        this.orderNumber = orderNumber;
    }
}