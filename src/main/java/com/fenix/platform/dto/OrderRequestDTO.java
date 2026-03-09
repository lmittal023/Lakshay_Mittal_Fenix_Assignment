package com.fenix.platform.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderRequestDTO {

    @NotBlank(message = "External Order ID is required")
    private String externalOrderId;

    private String externalOrderNumber;

    @Positive(message = "Amount must be greater than zero")
    private BigDecimal amount;

    private String currency;

    private String status;

    private String financialStatus;

    private String fulfillmentStatus;

    private String customerEmail;
}
