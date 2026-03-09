package com.fenix.platform.mapper;

import com.fenix.platform.dto.*;
import com.fenix.platform.entity.*;

public class DtoMapper {

public static Order toEntity(OrderRequestDTO dto) {
        if (dto == null)
            return null;
        Order order = new Order();
        order.setExternalOrderId(dto.getExternalOrderId());
        order.setExternalOrderNumber(dto.getExternalOrderNumber());
        order.setAmount(dto.getAmount());
        order.setCurrency(dto.getCurrency());
        order.setCustomerEmail(dto.getCustomerEmail());
        if (dto.getStatus() != null)
            order.setStatus(dto.getStatus());
        if (dto.getFinancialStatus() != null)
            order.setFinancialStatus(dto.getFinancialStatus());
        if (dto.getFulfillmentStatus() != null)
            order.setFulfillmentStatus(dto.getFulfillmentStatus());
        return order;
    }

    public static OrderResponseDTO toDto(Order entity) {
        if (entity == null)
            return null;
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(entity.getId());
        dto.setExternalOrderId(entity.getExternalOrderId());
        dto.setExternalOrderNumber(entity.getExternalOrderNumber());
        dto.setAmount(entity.getAmount());
        dto.setCurrency(entity.getCurrency());
        dto.setStatus(entity.getStatus());
        dto.setFinancialStatus(entity.getFinancialStatus());
        dto.setFulfillmentStatus(entity.getFulfillmentStatus());
        dto.setCustomerEmail(entity.getCustomerEmail());
        if (entity.getWebsite() != null)
            dto.setWebsiteId(entity.getWebsite().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

public static Company toEntity(CompanyRequestDTO dto) {
        if (dto == null)
            return null;
        Company company = new Company();
        company.setName(dto.getName());
        return company;
    }

    public static CompanyResponseDTO toDto(Company entity) {
        if (entity == null)
            return null;
        CompanyResponseDTO dto = new CompanyResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(entity.getStatus());
        return dto;
    }

public static Website toEntity(WebsiteRequestDTO dto) {
        if (dto == null)
            return null;
        Website w = new Website();
        w.setName(dto.getName());
        w.setDomain(dto.getDomain());
        w.setCode(dto.getCode());
        if (dto.getStatus() != null)
            w.setStatus(dto.getStatus());
        return w;
    }

    public static WebsiteResponseDTO toDto(Website entity) {
        if (entity == null)
            return null;
        WebsiteResponseDTO dto = new WebsiteResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDomain(entity.getDomain());
        dto.setCode(entity.getCode());
        dto.setStatus(entity.getStatus());
        if (entity.getCompany() != null)
            dto.setCompanyId(entity.getCompany().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

public static Fulfillment toEntity(FulfillmentRequestDTO dto) {
        if (dto == null)
            return null;
        Fulfillment f = new Fulfillment();
        f.setStatus(dto.getStatus());
        f.setTrackingNumber(dto.getTrackingNumber());
        return f;
    }

    public static FulfillmentResponseDTO toDto(Fulfillment entity) {
        if (entity == null)
            return null;
        FulfillmentResponseDTO dto = new FulfillmentResponseDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setTrackingNumber(entity.getTrackingNumber());
        dto.setCarrier(entity.getCarrier());
        if (entity.getOrder() != null)
            dto.setOrderId(entity.getOrder().getId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }

public static Tracking toEntity(TrackingRequestDTO dto) {
        if (dto == null)
            return null;
        Tracking t = new Tracking();
        t.setStatus(dto.getStatus());
        t.setLocation(dto.getLocation());
        t.setMessage(dto.getMessage());
        t.setEventTime(dto.getEventTime());
        return t;
    }

    public static TrackingResponseDTO toDto(Tracking entity) {
        if (entity == null)
            return null;
        TrackingResponseDTO dto = new TrackingResponseDTO();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus());
        dto.setLocation(entity.getLocation());
        dto.setMessage(entity.getMessage());
        dto.setEventTime(entity.getEventTime());
        if (entity.getFulfillment() != null)
            dto.setFulfillmentId(entity.getFulfillment().getId());
        return dto;
    }
}

