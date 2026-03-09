package com.fenix.platform.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CompanyResponseDTO {
    private UUID id;
    private String name;
    private String status;
}
