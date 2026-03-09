package com.fenix.platform.controller;

import com.fenix.platform.dto.CompanyRequestDTO;
import com.fenix.platform.dto.CompanyResponseDTO;
import com.fenix.platform.dto.OrderResponseDTO;
import com.fenix.platform.entity.Company;
import com.fenix.platform.entity.Order;
import com.fenix.platform.mapper.DtoMapper;
import com.fenix.platform.service.CompanyService;
import com.fenix.platform.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.UUID;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final OrderService orderService;

    @PostMapping
    public CompanyResponseDTO createCompany(@Valid @RequestBody CompanyRequestDTO companyDTO) {
        Company company = DtoMapper.toEntity(companyDTO);
        Company saved = companyService.createCompany(company);
        return DtoMapper.toDto(saved);
    }

    @GetMapping
    public List<CompanyResponseDTO> getAllCompanies() {
        return companyService.getAllCompanies().stream()
                .map(DtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyResponseDTO getCompanyById(@PathVariable("id") UUID id) {
        return DtoMapper.toDto(companyService.getCompanyById(id));
    }

    @PutMapping("/{id}")
    public CompanyResponseDTO updateCompany(@PathVariable("id") UUID id,
            @Valid @RequestBody CompanyRequestDTO dto) {
        return DtoMapper.toDto(companyService.updateCompany(id, dto));
    }

    @PatchMapping("/{id}")
    public CompanyResponseDTO patchCompany(@PathVariable("id") UUID id,
            @RequestBody CompanyRequestDTO dto) {
        return DtoMapper.toDto(companyService.patchCompany(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") UUID id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{companyId}/orders")
    public Page<OrderResponseDTO> getOrdersByCompany(@PathVariable("companyId") UUID companyId, Pageable pageable) {
        Page<Order> page = orderService.getOrdersByCompanyId(companyId, pageable);
        List<OrderResponseDTO> dtoList = page.getContent().stream()
                .map(DtoMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable, page.getTotalElements());
    }
}
