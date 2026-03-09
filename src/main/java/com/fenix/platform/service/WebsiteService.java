package com.fenix.platform.service;

import com.fenix.platform.dto.WebsiteRequestDTO;
import com.fenix.platform.entity.Company;
import com.fenix.platform.entity.Website;
import com.fenix.platform.exception.ResourceNotFoundException;
import com.fenix.platform.repository.CompanyRepository;
import com.fenix.platform.repository.WebsiteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WebsiteService {

    private final WebsiteRepository websiteRepository;
    private final CompanyRepository companyRepository;

    public Website createWebsite(WebsiteRequestDTO dto, UUID companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + companyId));
        Website website = new Website();
        website.setName(dto.getName());
        website.setDomain(dto.getDomain());
        website.setCode(dto.getCode());
        if (dto.getStatus() != null)
            website.setStatus(dto.getStatus());
        website.setCompany(company);
        return websiteRepository.save(website);
    }

    public List<Website> getAllWebsites() {
        return websiteRepository.findAll();
    }

    public Website getWebsiteById(UUID id) {
        return websiteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Website not found with id: " + id));
    }

    public Website updateWebsite(UUID id, WebsiteRequestDTO dto) {
        Website website = getWebsiteById(id);
        website.setName(dto.getName());
        website.setDomain(dto.getDomain());
        website.setCode(dto.getCode());
        if (dto.getStatus() != null)
            website.setStatus(dto.getStatus());
        return websiteRepository.save(website);
    }

    public Website patchWebsite(UUID id, WebsiteRequestDTO dto) {
        Website website = getWebsiteById(id);
        if (dto.getName() != null)
            website.setName(dto.getName());
        if (dto.getDomain() != null)
            website.setDomain(dto.getDomain());
        if (dto.getCode() != null)
            website.setCode(dto.getCode());
        if (dto.getStatus() != null)
            website.setStatus(dto.getStatus());
        return websiteRepository.save(website);
    }

    public void deleteWebsite(UUID id) {
        Website website = getWebsiteById(id);
        websiteRepository.delete(website);
    }
}
