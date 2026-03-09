package com.fenix.platform.controller;

import com.fenix.platform.dto.OrderRequestDTO;
import com.fenix.platform.dto.OrderResponseDTO;
import com.fenix.platform.dto.PagedResponse;
import com.fenix.platform.entity.Order;
import com.fenix.platform.mapper.DtoMapper;
import com.fenix.platform.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

        private final OrderService orderService;

        @PostMapping
        public OrderResponseDTO createOrder(@Valid @RequestBody OrderRequestDTO orderDTO,
                        @RequestParam("websiteId") UUID websiteId) {
                Order order = DtoMapper.toEntity(orderDTO);
                Order saved = orderService.createOrder(order, websiteId);
                return DtoMapper.toDto(saved);
        }

        @GetMapping
        public PagedResponse<OrderResponseDTO> getAllOrders(Pageable pageable) {
                Page<Order> page = orderService.getAllOrders(pageable);
                List<OrderResponseDTO> dtoList = page.getContent().stream()
                                .map(DtoMapper::toDto).collect(Collectors.toList());
                return new PagedResponse<>(dtoList, page.getNumber(), page.getSize(),
                                page.getTotalElements(), page.getTotalPages(), page.hasNext());
        }

        @GetMapping("/{id}")
        public OrderResponseDTO getById(@PathVariable("id") UUID id) {
                return DtoMapper.toDto(orderService.getOrderById(id));
        }

        @GetMapping("/company/{companyId}")
        public PagedResponse<OrderResponseDTO> getOrdersByCompany(
                        @PathVariable("companyId") UUID companyId, Pageable pageable) {
                Page<Order> page = orderService.getOrdersByCompanyId(companyId, pageable);
                List<OrderResponseDTO> dtoList = page.getContent().stream()
                                .map(DtoMapper::toDto).collect(Collectors.toList());
                return new PagedResponse<>(dtoList, page.getNumber(), page.getSize(),
                                page.getTotalElements(), page.getTotalPages(), page.isLast());
        }

        @GetMapping("/search")
        public PagedResponse<OrderResponseDTO> searchOrders(
                        @RequestParam UUID companyId,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                        Pageable pageable) {
                Page<Order> page = orderService.getOrdersByCompanyAndDateRange(companyId, startDate, endDate, pageable);
                List<OrderResponseDTO> dtoList = page.getContent().stream()
                                .map(DtoMapper::toDto).collect(Collectors.toList());
                return new PagedResponse<>(dtoList, page.getNumber(), page.getSize(),
                                page.getTotalElements(), page.getTotalPages(), page.isLast());
        }

        @PutMapping("/{id}")
        public OrderResponseDTO updateOrder(@PathVariable("id") UUID id,
                        @Valid @RequestBody OrderRequestDTO dto) {
                return DtoMapper.toDto(orderService.updateOrder(id, dto));
        }

        @PatchMapping("/{id}")
        public OrderResponseDTO patchOrder(@PathVariable("id") UUID id,
                        @RequestBody OrderRequestDTO dto) {
                return DtoMapper.toDto(orderService.patchOrder(id, dto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteOrder(@PathVariable("id") UUID id) {
                orderService.deleteOrder(id);
                return ResponseEntity.noContent().build();
        }
}
