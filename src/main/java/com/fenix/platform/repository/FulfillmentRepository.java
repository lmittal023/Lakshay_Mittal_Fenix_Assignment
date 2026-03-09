package com.fenix.platform.repository;

import com.fenix.platform.entity.Fulfillment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import java.util.UUID;

public interface FulfillmentRepository extends JpaRepository<Fulfillment, UUID> {

    Optional<Fulfillment> findByTrackingNumber(String trackingNumber);

    List<Fulfillment> findByOrderId(UUID orderId);
}