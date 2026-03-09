package com.fenix.platform.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WebsiteRequestDTO {

    @NotBlank(message = "Website name is required")
    private String name;

    @NotBlank(message = "Domain is required")
    private String domain;

    private String code;

    private String status = "ACTIVE";
}
