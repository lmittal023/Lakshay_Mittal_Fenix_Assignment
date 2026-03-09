package com.fenix.platform.repository;

import com.fenix.platform.entity.Website;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import java.util.UUID;

public interface WebsiteRepository extends JpaRepository<Website, UUID> {

    List<Website> findByCompanyId(UUID companyId);
}
