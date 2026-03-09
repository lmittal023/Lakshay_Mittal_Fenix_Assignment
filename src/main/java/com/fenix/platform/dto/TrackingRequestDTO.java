package com.fenix.platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackingRequestDTO {

    @NotBlank(message = "Status is required")
    private String status;

    private String location;

    private String message;

    private LocalDateTime eventTime;
}
