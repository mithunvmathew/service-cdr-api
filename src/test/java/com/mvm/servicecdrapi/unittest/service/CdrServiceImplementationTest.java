package com.mvm.servicecdrapi.unittest.service;

import com.mvm.servicecdrapi.controller.dto.ChargingDataRecord;
import com.mvm.servicecdrapi.exception.InvalidDataException;
import com.mvm.servicecdrapi.exception.RecordNotFoundException;
import com.mvm.servicecdrapi.repository.CdrRepository;
import com.mvm.servicecdrapi.repository.model.ChargingData;
import com.mvm.servicecdrapi.service.CdrServiceImplementation;
import com.mvm.servicecdrapi.service.mapper.ChargingDataMapper;
import com.mvm.servicecdrapi.unittest.faker.ChargingDataRecordFaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CdrServiceImplementationTest {

    @Mock
    CdrRepository cdrRepository;
    @Mock
    ChargingDataMapper chargingDataMapper;
    @InjectMocks
    CdrServiceImplementation cdrService;

    @Test
    @DisplayName("Given cdr request  When calling service to createCdr Then service will call the repository to save the cdr")
    public void createCdrTest() {
        //Given
        ChargingData chargingData = new ChargingData();
        when(cdrRepository.findLatestCdr("JT8BH68X8Y0026877")).thenReturn(Optional.of(chargingData));
        when(chargingDataMapper.toDto(chargingData)).thenReturn(ChargingDataRecordFaker.fakerRequest());
        when(chargingDataMapper.toEntity(ChargingDataRecordFaker.fakerRequest())).thenReturn(chargingData);
        when(chargingDataMapper.toEntity(ChargingDataRecordFaker.fakerRequestWithHigherStartTime())).thenReturn(chargingData);
        //When
        cdrService.createCdr(ChargingDataRecordFaker.fakerRequestWithHigherStartTime());

        //Then
        Mockito.verify(cdrRepository).save(chargingData);
    }

    @Test
    @DisplayName("Given cdr request with invalid data  When calling service to createCdr Then service will throw InvalidDataException")
    public void createCdrWithInvalidDataTest() {
        assertThrows(InvalidDataException.class, () -> cdrService.createCdr(ChargingDataRecordFaker.fakerRequestWithInvalidData()));
    }

    @Test
    @DisplayName("Given cdr request is not the latest request  When calling service to createCdr Then service will throw InvalidDataException")
    public void createCdrWithOldDataTest() {
        //Given
        ChargingData chargingData = new ChargingData();
        when(cdrRepository.findLatestCdr("JT8BH68X8Y0026877")).thenReturn(Optional.of(chargingData));
        when(chargingDataMapper.toDto(chargingData)).thenReturn(ChargingDataRecordFaker.fakerRequest());
        when(chargingDataMapper.toEntity(ChargingDataRecordFaker.fakerRequest())).thenReturn(chargingData);

        //When
        assertThrows(InvalidDataException.class, () -> cdrService.createCdr(ChargingDataRecordFaker.fakerRequest()));
    }

    @Test
    @DisplayName("Given cdr id  When calling service to getCdrById Then will return response with valid data")
    public void getCdrByIdTest() {
        //Given
        ChargingData chargingData = new ChargingData();
        when(cdrRepository.findById(UUID.fromString("8d09dbb5-9431-41a5-a64f-70c996c48e28"))).thenReturn(Optional.of(chargingData));
        when(chargingDataMapper.toDto(chargingData)).thenReturn(ChargingDataRecordFaker.fakerResponse());

        //When
        ChargingDataRecord response = cdrService.getCdr(UUID.fromString("8d09dbb5-9431-41a5-a64f-70c996c48e28"));

        //Then
        assertEquals(UUID.fromString("8d09dbb5-9431-41a5-a64f-70c996c48e28"), response.id());
    }

    @Test
    @DisplayName("Given cdr id which is not exist When calling service to getCdrById Then service will throw RecordNotFoundException")
    public void getCdrByIdNotFoundDataTest() {
        //Given
        ChargingData chargingData = new ChargingData();
        when(cdrRepository.findById(UUID.fromString("8d09dbb5-9431-41a5-a64f-70c996c48e28"))).thenReturn(Optional.ofNullable(null));

        //When
        assertThrows(RecordNotFoundException.class, () -> cdrService.getCdr(UUID.fromString("8d09dbb5-9431-41a5-a64f-70c996c48e28")));

    }

    @Test
    @DisplayName("Given vehicle Id  When calling service to getAllCdrByVehicleId Then will return response with valid list of data")
    public void getAllCdrByVehicleIdTest() {
        //Given
        ChargingData chargingData1 = new ChargingData();
        ChargingData chargingData2 = new ChargingData();
        Sort sort = Sort.by(Sort.Direction.ASC, "startTime").and(Sort.by(Sort.Direction.ASC, "endTime"));
        when(cdrRepository.searchAll("JT8BH68X8Y0026877", sort)).thenReturn(List.of(chargingData1, chargingData2));
        when(chargingDataMapper.toDto(chargingData1)).thenReturn(ChargingDataRecordFaker.fakerResponse());
        when(chargingDataMapper.toDto(chargingData2)).thenReturn(ChargingDataRecordFaker.fakerResponseWithDifferentId());

        //When
        List<ChargingDataRecord> response = cdrService.getAllCdrByVehicleId("JT8BH68X8Y0026877", Sort.Direction.ASC, Sort.Direction.ASC);

        //Then
        assertEquals(2, response.size());
    }

    @Test
    @DisplayName("Given vehicle Id is not existing  When calling service to getAllCdrByVehicleId Then will return empty list")
    public void getAllCdrByVehicleIdNotExistingTest() {
        //Given
        ChargingData chargingData1 = new ChargingData();
        ChargingData chargingData2 = new ChargingData();
        Sort sort = Sort.by(Sort.Direction.ASC, "startTime").and(Sort.by(Sort.Direction.ASC, "endTime"));
        when(cdrRepository.searchAll("JT8BH68X8Y0026877", sort)).thenReturn(new ArrayList<>());

        //When
        List<ChargingDataRecord> response = cdrService.getAllCdrByVehicleId("JT8BH68X8Y0026877", Sort.Direction.ASC, Sort.Direction.ASC);

        //Then
        assertTrue(response.isEmpty());
    }
}
