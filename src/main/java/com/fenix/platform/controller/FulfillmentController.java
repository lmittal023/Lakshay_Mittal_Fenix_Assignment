package com.fenix.platform.controller;

import com.fenix.platform.dto.FulfillmentRequestDTO;
import com.fenix.platform.dto.FulfillmentResponseDTO;
import com.fenix.platform.entity.Fulfillment;
import com.fenix.platform.mapper.DtoMapper;
import com.fenix.platform.service.FulfillmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@RestController
@RequestMapping("/fulfillments")
@RequiredArgsConstructor
public class FulfillmentController {

    private final FulfillmentService fulfillmentService;

    @PostMapping("/{orderId}")
    public FulfillmentResponseDTO createFulfillment(
            @PathVariable("orderId") UUID orderId,
            @Valid @RequestBody FulfillmentRequestDTO dto) {
        Fulfillment saved = fulfillmentService.createFulfillment(orderId, dto);
        return DtoMapper.toDto(saved);
    }

    @GetMapping
    public List<FulfillmentResponseDTO> getAllFulfillments() {
        return fulfillmentService.getAllFulfillments().stream()
                .map(DtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FulfillmentResponseDTO getById(@PathVariable("id") UUID id) {
        return DtoMapper.toDto(fulfillmentService.getFulfillmentById(id));
    }

    @GetMapping("/order/{orderId}")
    public List<FulfillmentResponseDTO> getByOrder(@PathVariable("orderId") UUID orderId) {
        return fulfillmentService.getByOrderId(orderId).stream()
                .map(DtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/tracking/{trackingNumber}")
    public FulfillmentResponseDTO getByTracking(@PathVariable("trackingNumber") String trackingNumber) {
        return DtoMapper.toDto(fulfillmentService.getByTrackingNumber(trackingNumber));
    }

    @PutMapping("/{id}")
    public FulfillmentResponseDTO updateFulfillment(@PathVariable("id") UUID id,
            @Valid @RequestBody FulfillmentRequestDTO dto) {
        return DtoMapper.toDto(fulfillmentService.updateFulfillment(id, dto));
    }

    @PatchMapping("/{id}")
    public FulfillmentResponseDTO patchFulfillment(@PathVariable("id") UUID id,
            @RequestBody FulfillmentRequestDTO dto) {
        return DtoMapper.toDto(fulfillmentService.patchFulfillment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFulfillment(@PathVariable("id") UUID id) {
        fulfillmentService.deleteFulfillment(id);
        return ResponseEntity.noContent().build();
    }
}
