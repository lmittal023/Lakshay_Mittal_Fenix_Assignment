package com.fenix.platform.service;

import com.fenix.platform.dto.FulfillmentRequestDTO;
import com.fenix.platform.entity.Fulfillment;
import com.fenix.platform.entity.Order;
import com.fenix.platform.exception.ResourceNotFoundException;
import com.fenix.platform.repository.FulfillmentRepository;
import com.fenix.platform.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FulfillmentService {

    private final FulfillmentRepository fulfillmentRepository;
    private final OrderRepository orderRepository;

    public Fulfillment createFulfillment(UUID orderId, FulfillmentRequestDTO dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        Fulfillment fulfillment = new Fulfillment();
        fulfillment.setStatus(dto.getStatus());
        fulfillment.setTrackingNumber(dto.getTrackingNumber());
        fulfillment.setOrder(order);
        return fulfillmentRepository.save(fulfillment);
    }

    public List<Fulfillment> getAllFulfillments() {
        return fulfillmentRepository.findAll();
    }

    public Fulfillment getFulfillmentById(UUID id) {
        return fulfillmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fulfillment not found with id: " + id));
    }

    public List<Fulfillment> getByOrderId(UUID orderId) {
        return fulfillmentRepository.findByOrderId(orderId);
    }

    public Fulfillment getByTrackingNumber(String trackingNumber) {
        return fulfillmentRepository
                .findByTrackingNumber(trackingNumber)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Fulfillment not found with tracking: " + trackingNumber));
    }

    public Fulfillment updateFulfillment(UUID id, FulfillmentRequestDTO dto) {
        Fulfillment fulfillment = getFulfillmentById(id);
        if (dto.getStatus() != null)
            fulfillment.setStatus(dto.getStatus());
        if (dto.getTrackingNumber() != null)
            fulfillment.setTrackingNumber(dto.getTrackingNumber());
        return fulfillmentRepository.save(fulfillment);
    }

    public Fulfillment patchFulfillment(UUID id, FulfillmentRequestDTO dto) {
        return updateFulfillment(id, dto);
    }

    public void deleteFulfillment(UUID id) {
        Fulfillment fulfillment = getFulfillmentById(id);
        fulfillmentRepository.delete(fulfillment);
    }
}
