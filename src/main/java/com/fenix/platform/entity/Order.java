package com.fenix.platform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    @Column(name = "external_order_id")
    private String externalOrderId;

    @Column(name = "external_order_number")
    private String externalOrderNumber;

    @Column(precision = 12, scale = 2)
    private BigDecimal amount;

    private String currency;

    @Column(name = "order_status", columnDefinition = "VARCHAR(20) DEFAULT 'CREATED'")
    private String status = "CREATED";

    @Column(name = "financial_status", columnDefinition = "VARCHAR(30) DEFAULT 'PENDING'")
    private String financialStatus = "PENDING";

    @Column(name = "fulfillment_status", columnDefinition = "VARCHAR(20) DEFAULT 'UNFULFILLED'")
    private String fulfillmentStatus = "UNFULFILLED";

    @Column(name = "customer_email")
    private String customerEmail;

    @ManyToOne
    @JoinColumn(name = "website_id")
    @JsonBackReference
    private Website website;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private java.util.List<Fulfillment> fulfillments = new java.util.ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
