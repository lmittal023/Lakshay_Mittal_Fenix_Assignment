package com.fenix.platform.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import java.util.UUID;

@Data
public class OrderResponseDTO {
    private UUID id;
    private String externalOrderId;
    private String externalOrderNumber;
    private BigDecimal amount;
    private String currency;
    private String status;
    private String financialStatus;
    private String fulfillmentStatus;
    private String customerEmail;
    private UUID websiteId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
