package com.fenix.platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FulfillmentRequestDTO {

    @NotBlank(message = "Tracking number is required")
    private String trackingNumber;

    @NotBlank(message = "Status is required")
    private String status;
}