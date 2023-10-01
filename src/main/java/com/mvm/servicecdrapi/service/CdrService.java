package com.mvm.servicecdrapi.service;

import com.mvm.servicecdrapi.controller.dto.ChargingDataRecord;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface CdrService {

    ChargingDataRecord createCdr(ChargingDataRecord chargingDataRecord);

    ChargingDataRecord getCdr(UUID id);

    List<ChargingDataRecord> getAllCdrByVehicleId(String vehicleId, Sort.Direction byStartTime, Sort.Direction byEndTime);

}
