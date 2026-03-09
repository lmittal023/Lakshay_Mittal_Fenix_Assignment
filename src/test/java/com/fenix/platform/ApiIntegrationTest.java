package com.fenix.platform;

import com.fenix.platform.dto.CompanyRequestDTO;
import com.fenix.platform.dto.CompanyResponseDTO;
import com.fenix.platform.dto.OrderRequestDTO;
import com.fenix.platform.dto.OrderResponseDTO;
import com.fenix.platform.dto.WebsiteRequestDTO;
import com.fenix.platform.dto.WebsiteResponseDTO;
import com.fenix.platform.repository.CompanyRepository;
import com.fenix.platform.repository.OrderRepository;
import com.fenix.platform.repository.WebsiteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("local") // Uses application-local.properties with H2 config
public class ApiIntegrationTest {

        @Autowired
        private TestRestTemplate restTemplate;

        @Autowired
        private CompanyRepository companyRepository;

        @Autowired
        private WebsiteRepository websiteRepository;

        @Autowired
        private OrderRepository orderRepository;

        @BeforeEach
        void setUp() {
                orderRepository.deleteAll();
                websiteRepository.deleteAll();
                companyRepository.deleteAll();
        }

        @Test
        void testCompleteApiFlow() {
                // 1. Create a Company
                CompanyRequestDTO compReq = new CompanyRequestDTO();
                compReq.setName("Test Company");

                ResponseEntity<CompanyResponseDTO> compRes = restTemplate.postForEntity("/organizations", compReq,
                                CompanyResponseDTO.class);
                assertEquals(HttpStatus.OK, compRes.getStatusCode());
                assertNotNull(compRes.getBody());
                UUID companyId = compRes.getBody().getId();
                assertNotNull(companyId);

                // 2. Update the Company (PUT)
                compReq.setName("Updated Test Company");
                HttpEntity<CompanyRequestDTO> requestEntity = new HttpEntity<>(compReq);
                ResponseEntity<CompanyResponseDTO> updatedCompRes = restTemplate.exchange("/organizations/" + companyId,
                                HttpMethod.PUT, requestEntity, CompanyResponseDTO.class);
                assertEquals(HttpStatus.OK, updatedCompRes.getStatusCode());
                assertEquals("Updated Test Company", updatedCompRes.getBody().getName());

                // 3. Create a Website for the company
                WebsiteRequestDTO websiteReq = new WebsiteRequestDTO();
                websiteReq.setName("Main Store");
                websiteReq.setDomain("store.example.com");
                websiteReq.setCode("STORE123");
                ResponseEntity<WebsiteResponseDTO> webRes = restTemplate.postForEntity(
                                "/websites?companyId=" + companyId, websiteReq, WebsiteResponseDTO.class);
                assertEquals(HttpStatus.OK, webRes.getStatusCode());
                assertNotNull(webRes.getBody());
                UUID websiteId = webRes.getBody().getId();
                assertNotNull(websiteId);

                // 4. Create an Order
                OrderRequestDTO ordReq = new OrderRequestDTO();
                ordReq.setExternalOrderId("EXT-001");
                ordReq.setAmount(new java.math.BigDecimal("99.99"));

                ResponseEntity<OrderResponseDTO> ordRes = restTemplate.postForEntity("/orders?websiteId=" + websiteId,
                                ordReq,
                                OrderResponseDTO.class);
                assertEquals(HttpStatus.OK, ordRes.getStatusCode());
                assertNotNull(ordRes.getBody());
                UUID orderId = ordRes.getBody().getId();
                assertNotNull(orderId);

                // 5. Get orders by company
                ResponseEntity<Map> companyOrdersRes = restTemplate.getForEntity("/orders/company/" + companyId,
                                Map.class);
                assertEquals(HttpStatus.OK, companyOrdersRes.getStatusCode());
                assertNotNull(companyOrdersRes.getBody());
                List<?> content = (List<?>) companyOrdersRes.getBody().get("content");
                assertTrue(content.size() > 0, "Should have at least 1 order");

                // 6. Delete Order
                ResponseEntity<Void> deleteRes = restTemplate.exchange("/orders/" + orderId, HttpMethod.DELETE, null,
                                Void.class);
                assertEquals(HttpStatus.NO_CONTENT, deleteRes.getStatusCode());
        }
}
