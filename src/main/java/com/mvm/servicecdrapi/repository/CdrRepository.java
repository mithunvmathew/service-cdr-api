package com.mvm.servicecdrapi.repository;

import com.mvm.servicecdrapi.repository.model.ChargingData;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CdrRepository extends JpaRepository<ChargingData, UUID> {

    @Query("FROM ChargingData WHERE vehicleId = ?1")
    List<ChargingData> searchAll(String vehicleId, Sort sort);

    @Query("FROM ChargingData WHERE vehicleId = ?1 ORDER BY endTime DESC LIMIT 1")
    Optional<ChargingData> findLatestCdr(String vehicleId);

}
