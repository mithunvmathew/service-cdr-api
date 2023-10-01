package com.mvm.servicecdrapi.repository.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Table(name = "charging_data")
public class ChargingData extends AuditData {
    @Id
    private UUID id = UUID.randomUUID();
    @Column(name = "vehicle_id")
    private String vehicleId;
    @Column(name = "session_id")
    private UUID sessionId;
    @Column(name = "start_time")
    private Instant startTime;
    @Column(name = "end_time")
    private Instant endTime;
    @Column(name = "amount")
    private Double totalAmount;
}
