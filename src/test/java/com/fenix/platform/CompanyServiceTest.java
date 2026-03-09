package com.fenix.platform;

import com.fenix.platform.entity.Company;
import com.fenix.platform.exception.ResourceNotFoundException;
import com.fenix.platform.repository.CompanyRepository;
import com.fenix.platform.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CompanyServiceTest {

    @Mock
    private CompanyRepository companyRepository;

    @InjectMocks
    private CompanyService companyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCompany() {
        Company company = new Company();
        company.setName("Test Corp");

        when(companyRepository.save(any(Company.class))).thenReturn(company);

        Company result = companyService.createCompany(company);

        assertNotNull(result);
        assertEquals("Test Corp", result.getName());
        verify(companyRepository, times(1)).save(company);
    }

    @Test
    void testGetAllCompanies() {
        Company c1 = new Company();
        c1.setName("Corp A");
        Company c2 = new Company();
        c2.setName("Corp B");

        when(companyRepository.findAll()).thenReturn(List.of(c1, c2));

        List<Company> result = companyService.getAllCompanies();

        assertEquals(2, result.size());
        assertEquals("Corp A", result.get(0).getName());
    }

    @Test
    void testGetCompanyById_Found() {
        UUID id = UUID.randomUUID();
        Company company = new Company();
        company.setId(id);
        company.setName("Found Corp");

        when(companyRepository.findById(id)).thenReturn(Optional.of(company));

        Company result = companyService.getCompanyById(id);

        assertNotNull(result);
        assertEquals("Found Corp", result.getName());
    }

    @Test
    void testGetCompanyById_NotFound() {
        UUID id = UUID.randomUUID();
        when(companyRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> companyService.getCompanyById(id));
    }

    @Test
    void testDeleteCompany() {
        UUID id = UUID.randomUUID();
        Company company = new Company();
        company.setId(id);
        company.setName("To Delete");

        when(companyRepository.findById(id)).thenReturn(Optional.of(company));
        doNothing().when(companyRepository).delete(company);

        assertDoesNotThrow(() -> companyService.deleteCompany(id));
        verify(companyRepository, times(1)).delete(company);
    }
}
