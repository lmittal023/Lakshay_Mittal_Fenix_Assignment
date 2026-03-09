package com.fenix.platform.dto;

import lombok.Data;

import java.time.LocalDateTime;

import java.util.UUID;

@Data
public class WebsiteResponseDTO {
    private UUID id;
    private String name;
    private String domain;
    private String code;
    private String status;
    private UUID companyId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
