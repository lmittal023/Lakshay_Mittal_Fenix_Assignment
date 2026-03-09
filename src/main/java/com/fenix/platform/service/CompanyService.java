package com.fenix.platform.service;

import com.fenix.platform.dto.CompanyRequestDTO;
import com.fenix.platform.entity.Company;
import com.fenix.platform.exception.ResourceNotFoundException;
import com.fenix.platform.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public Company getCompanyById(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + id));
    }

    public Company updateCompany(UUID id, CompanyRequestDTO dto) {
        Company company = getCompanyById(id);
        company.setName(dto.getName());
        return companyRepository.save(company);
    }

    public Company patchCompany(UUID id, CompanyRequestDTO dto) {
        Company company = getCompanyById(id);
        if (dto.getName() != null)
            company.setName(dto.getName());
        return companyRepository.save(company);
    }

    public void deleteCompany(UUID id) {
        Company company = getCompanyById(id);
        companyRepository.delete(company);
    }
}
