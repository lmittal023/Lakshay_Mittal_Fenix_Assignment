package com.fenix.platform.dto;

import lombok.Data;

import java.time.LocalDateTime;

import java.util.UUID;

@Data
public class TrackingResponseDTO {
    private UUID id;
    private String status;
    private String location;
    private String message;
    private LocalDateTime eventTime;
    private UUID fulfillmentId;
}
