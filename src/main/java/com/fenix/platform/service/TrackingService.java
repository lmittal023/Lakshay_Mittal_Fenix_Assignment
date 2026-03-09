package com.fenix.platform.service;

import com.fenix.platform.dto.TrackingRequestDTO;
import com.fenix.platform.entity.Fulfillment;
import com.fenix.platform.entity.Tracking;
import com.fenix.platform.exception.ResourceNotFoundException;
import com.fenix.platform.repository.FulfillmentRepository;
import com.fenix.platform.repository.TrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final TrackingRepository trackingRepository;
    private final FulfillmentRepository fulfillmentRepository;

    public Tracking createTracking(UUID fulfillmentId, TrackingRequestDTO dto) {
        Fulfillment fulfillment = fulfillmentRepository.findById(fulfillmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Fulfillment not found with id: " + fulfillmentId));
        Tracking tracking = new Tracking();
        tracking.setStatus(dto.getStatus());
        tracking.setLocation(dto.getLocation());
        tracking.setMessage(dto.getMessage());
        tracking.setEventTime(dto.getEventTime());
        tracking.setFulfillment(fulfillment);
        return trackingRepository.save(tracking);
    }

    public List<Tracking> getByFulfillmentId(UUID fulfillmentId) {
        return trackingRepository.findByFulfillmentId(fulfillmentId);
    }

    public Tracking getTrackingById(UUID id) {
        return trackingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tracking event not found with id: " + id));
    }

    public Tracking updateTracking(UUID id, TrackingRequestDTO dto) {
        Tracking tracking = getTrackingById(id);
        if (dto.getStatus() != null)
            tracking.setStatus(dto.getStatus());
        if (dto.getLocation() != null)
            tracking.setLocation(dto.getLocation());
        if (dto.getMessage() != null)
            tracking.setMessage(dto.getMessage());
        if (dto.getEventTime() != null)
            tracking.setEventTime(dto.getEventTime());
        return trackingRepository.save(tracking);
    }

    public Tracking patchTracking(UUID id, TrackingRequestDTO dto) {
        return updateTracking(id, dto);
    }

    public void deleteTracking(UUID id) {
        Tracking tracking = getTrackingById(id);
        trackingRepository.delete(tracking);
    }
}
