package com.fenix.platform.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tracking_events")
public class Tracking {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private java.util.UUID id;

    private String status;

    private String location;

    private String message;

    @Column(name = "event_time")
    private LocalDateTime eventTime;

    @ManyToOne
    @JoinColumn(name = "fulfillment_id")
    @JsonBackReference
    private Fulfillment fulfillment;

    @PrePersist
    public void prePersist() {
        if (this.eventTime == null) {
            this.eventTime = LocalDateTime.now();
        }
    }
}
