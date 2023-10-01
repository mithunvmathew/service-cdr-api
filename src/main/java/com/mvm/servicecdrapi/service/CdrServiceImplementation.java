package com.mvm.servicecdrapi.service;

import com.mvm.servicecdrapi.controller.dto.ChargingDataRecord;
import com.mvm.servicecdrapi.exception.InvalidDataException;
import com.mvm.servicecdrapi.exception.RecordNotFoundException;
import com.mvm.servicecdrapi.repository.CdrRepository;
import com.mvm.servicecdrapi.repository.model.ChargingData;
import com.mvm.servicecdrapi.service.mapper.ChargingDataMapper;
import org.antlr.v4.runtime.atn.SemanticContext;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CdrServiceImplementation implements CdrService {

    private final CdrRepository cdrRepository;
    private final ChargingDataMapper chargingDataMapper;

    public CdrServiceImplementation(CdrRepository cdrRepository, ChargingDataMapper chargingDataMapper) {
        this.cdrRepository = cdrRepository;
        this.chargingDataMapper = chargingDataMapper;
    }

    @Override
    public ChargingDataRecord createCdr(ChargingDataRecord chargingDataRecord) {
        if (!isValidStartAndEndTime(chargingDataRecord)) {
            throw new InvalidDataException("EndTime must be greater than StartTime");
        }
        if (chargingDataRecord.totalAmount() <= Double.MIN_VALUE) {
            throw new InvalidDataException("Amount must be greater than zero");
        }
        if (!isLatestRequest(chargingDataRecord)) {
            throw new InvalidDataException("Requested data is not the latest cdr");
        }
        return chargingDataMapper.toDto(
                    cdrRepository.save(chargingDataMapper.toEntity(chargingDataRecord)));
    }

    @Override
    public ChargingDataRecord getCdr(UUID id) {
        return chargingDataMapper.toDto(
                cdrRepository.findById(id).orElseThrow(
                        () -> new RecordNotFoundException("Requested cdr with id : " + id + " not found")));
    }

    @Override
    public List<ChargingDataRecord> getAllCdrByVehicleId(String vehicleId,
                                                         Sort.Direction byStartTime,
                                                         Sort.Direction byEndTime) {
        Sort sortingOrder = Sort.by(byStartTime, "startTime")
                .and(Sort.by(byStartTime, "endTime"));
        return cdrRepository.searchAll(vehicleId, sortingOrder)
                .stream()
                .map(chargingDataMapper::toDto)
                .collect(Collectors.toList());

    }

    private boolean isValidStartAndEndTime(ChargingDataRecord chargingDataRecord) {
     return chargingDataRecord.endTime().isAfter(chargingDataRecord.startTime());
    }

    public boolean isLatestRequest(ChargingDataRecord chargingDataRecord) {
        Optional<ChargingData> latestRecord = cdrRepository.findLatestCdr(chargingDataRecord.vehicleId());
       return !latestRecord.isPresent() || latestRecord.filter(chargingData -> chargingDataMapper.toDto(chargingData).endTime()
               .isBefore(chargingDataRecord.startTime())).isPresent();


    }
}
