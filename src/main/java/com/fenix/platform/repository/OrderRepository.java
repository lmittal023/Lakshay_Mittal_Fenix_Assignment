package com.fenix.platform.repository;

import com.fenix.platform.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByWebsiteId(UUID websiteId);

    Page<Order> findByWebsiteId(UUID websiteId, Pageable pageable);

    Page<Order> findByWebsiteIdAndCreatedAtBetween(
            UUID websiteId,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable);

    Page<Order> findByWebsite_Company_Id(UUID companyId, Pageable pageable);

    Page<Order> findByWebsite_Company_IdAndCreatedAtBetween(
            UUID companyId,
            LocalDateTime start,
            LocalDateTime end,
            Pageable pageable);

    Optional<Order> findByWebsiteIdAndExternalOrderId(UUID websiteId, String externalOrderId);
}
