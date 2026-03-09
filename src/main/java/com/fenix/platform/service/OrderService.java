package com.fenix.platform.service;

import com.fenix.platform.dto.OrderRequestDTO;
import com.fenix.platform.entity.Order;
import com.fenix.platform.entity.Website;
import com.fenix.platform.exception.ResourceNotFoundException;
import com.fenix.platform.repository.OrderRepository;
import com.fenix.platform.repository.WebsiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebsiteRepository websiteRepository;

    public Order createOrder(Order order, UUID websiteId) {
        Website website = websiteRepository.findById(websiteId)
                .orElseThrow(() -> new ResourceNotFoundException("Website not found with id: " + websiteId));

        if (order.getExternalOrderId() != null) {
            Optional<Order> existing = orderRepository
                    .findByWebsiteIdAndExternalOrderId(websiteId, order.getExternalOrderId());
            if (existing.isPresent()) {
                Order toUpdate = existing.get();
                if (order.getAmount() != null)
                    toUpdate.setAmount(order.getAmount());
                if (order.getStatus() != null)
                    toUpdate.setStatus(order.getStatus());
                if (order.getFinancialStatus() != null)
                    toUpdate.setFinancialStatus(order.getFinancialStatus());
                if (order.getFulfillmentStatus() != null)
                    toUpdate.setFulfillmentStatus(order.getFulfillmentStatus());
                if (order.getCustomerEmail() != null)
                    toUpdate.setCustomerEmail(order.getCustomerEmail());
                if (order.getCurrency() != null)
                    toUpdate.setCurrency(order.getCurrency());
                if (order.getExternalOrderNumber() != null)
                    toUpdate.setExternalOrderNumber(order.getExternalOrderNumber());
                return orderRepository.save(toUpdate);
            }
        }
        order.setWebsite(website);
        return orderRepository.save(order);
    }

    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order getOrderById(UUID id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    public Page<Order> getOrdersByCompanyId(UUID companyId, Pageable pageable) {
        return orderRepository.findByWebsite_Company_Id(companyId, pageable);
    }

    public Page<Order> getOrdersByCompanyAndDateRange(
            UUID companyId, LocalDateTime start, LocalDateTime end, Pageable pageable) {
        return orderRepository.findByWebsite_Company_IdAndCreatedAtBetween(companyId, start, end, pageable);
    }

    public Order updateOrder(UUID id, OrderRequestDTO dto) {
        Order order = getOrderById(id);
        if (dto.getExternalOrderId() != null)
            order.setExternalOrderId(dto.getExternalOrderId());
        if (dto.getExternalOrderNumber() != null)
            order.setExternalOrderNumber(dto.getExternalOrderNumber());
        if (dto.getAmount() != null)
            order.setAmount(dto.getAmount());
        if (dto.getCurrency() != null)
            order.setCurrency(dto.getCurrency());
        if (dto.getStatus() != null)
            order.setStatus(dto.getStatus());
        if (dto.getFinancialStatus() != null)
            order.setFinancialStatus(dto.getFinancialStatus());
        if (dto.getFulfillmentStatus() != null)
            order.setFulfillmentStatus(dto.getFulfillmentStatus());
        if (dto.getCustomerEmail() != null)
            order.setCustomerEmail(dto.getCustomerEmail());
        return orderRepository.save(order);
    }

    public Order patchOrder(UUID id, OrderRequestDTO dto) {
        return updateOrder(id, dto);
    }

    public void deleteOrder(UUID id) {
        Order order = getOrderById(id);
        orderRepository.delete(order);
    }
}
