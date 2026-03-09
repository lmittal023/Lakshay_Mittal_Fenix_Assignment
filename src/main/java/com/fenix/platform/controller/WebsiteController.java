package com.fenix.platform.controller;

import com.fenix.platform.dto.WebsiteRequestDTO;
import com.fenix.platform.dto.WebsiteResponseDTO;
import com.fenix.platform.entity.Website;
import com.fenix.platform.mapper.DtoMapper;
import com.fenix.platform.service.WebsiteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@RestController
@RequestMapping("/websites")
@RequiredArgsConstructor
public class WebsiteController {

    private final WebsiteService websiteService;

    @PostMapping
    public WebsiteResponseDTO createWebsite(
            @Valid @RequestBody WebsiteRequestDTO dto,
            @RequestParam("companyId") UUID companyId) {
        Website website = websiteService.createWebsite(dto, companyId);
        return DtoMapper.toDto(website);
    }

    @GetMapping
    public List<WebsiteResponseDTO> getAllWebsites() {
        return websiteService.getAllWebsites().stream()
                .map(DtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public WebsiteResponseDTO getWebsiteById(@PathVariable("id") UUID id) {
        return DtoMapper.toDto(websiteService.getWebsiteById(id));
    }

    @PutMapping("/{id}")
    public WebsiteResponseDTO updateWebsite(@PathVariable("id") UUID id,
            @Valid @RequestBody WebsiteRequestDTO dto) {
        return DtoMapper.toDto(websiteService.updateWebsite(id, dto));
    }

    @PatchMapping("/{id}")
    public WebsiteResponseDTO patchWebsite(@PathVariable("id") UUID id,
            @RequestBody WebsiteRequestDTO dto) {
        return DtoMapper.toDto(websiteService.patchWebsite(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWebsite(@PathVariable("id") UUID id) {
        websiteService.deleteWebsite(id);
        return ResponseEntity.noContent().build();
    }
}
