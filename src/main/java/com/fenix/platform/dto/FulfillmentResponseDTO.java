package com.fenix.platform.dto;

import lombok.Data;

import java.time.LocalDateTime;

import java.util.UUID;

@Data
public class FulfillmentResponseDTO {
    private UUID id;
    private String status;
    private String trackingNumber;
    private String carrier;
    private UUID orderId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}