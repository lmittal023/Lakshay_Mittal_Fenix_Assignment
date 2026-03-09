package com.fenix.platform.controller;

import com.fenix.platform.dto.TrackingRequestDTO;
import com.fenix.platform.dto.TrackingResponseDTO;
import com.fenix.platform.entity.Tracking;
import com.fenix.platform.mapper.DtoMapper;
import com.fenix.platform.service.TrackingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@RestController
@RequestMapping("/tracking")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;

    @PostMapping("/fulfillments/{fulfillmentId}")
    public TrackingResponseDTO createTracking(
            @PathVariable("fulfillmentId") UUID fulfillmentId,
            @Valid @RequestBody TrackingRequestDTO dto) {
        Tracking saved = trackingService.createTracking(fulfillmentId, dto);
        return DtoMapper.toDto(saved);
    }

    @GetMapping("/fulfillments/{fulfillmentId}")
    public List<TrackingResponseDTO> getByFulfillment(@PathVariable("fulfillmentId") UUID fulfillmentId) {
        return trackingService.getByFulfillmentId(fulfillmentId).stream()
                .map(DtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TrackingResponseDTO getById(@PathVariable("id") UUID id) {
        return DtoMapper.toDto(trackingService.getTrackingById(id));
    }

    @PutMapping("/{id}")
    public TrackingResponseDTO updateTracking(@PathVariable("id") UUID id,
            @Valid @RequestBody TrackingRequestDTO dto) {
        return DtoMapper.toDto(trackingService.updateTracking(id, dto));
    }

    @PatchMapping("/{id}")
    public TrackingResponseDTO patchTracking(@PathVariable("id") UUID id,
            @RequestBody TrackingRequestDTO dto) {
        return DtoMapper.toDto(trackingService.patchTracking(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTracking(@PathVariable("id") UUID id) {
        trackingService.deleteTracking(id);
        return ResponseEntity.noContent().build();
    }
}
