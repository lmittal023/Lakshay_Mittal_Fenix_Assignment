package com.fenix.platform.repository;

import com.fenix.platform.entity.Tracking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import java.util.UUID;

public interface TrackingRepository extends JpaRepository<Tracking, UUID> {

    List<Tracking> findByFulfillmentId(UUID fulfillmentId);

}