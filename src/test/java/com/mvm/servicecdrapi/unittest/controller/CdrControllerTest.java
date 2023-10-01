package com.mvm.servicecdrapi.unittest.controller;

import com.mvm.servicecdrapi.controller.CdrController;
import com.mvm.servicecdrapi.controller.dto.ChargingDataRecord;
import com.mvm.servicecdrapi.service.CdrService;
import com.mvm.servicecdrapi.unittest.faker.ChargingDataRecordFaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CdrControllerTest {

    @Mock
    CdrService cdrService;

    @InjectMocks
    CdrController cdrController;

    @Test
    @DisplayName("Given cdr request When calling controller Then service will get cdr with valid cdr id")
    public void createCdrTest() {
        //Given

        when(cdrService.createCdr(ChargingDataRecordFaker.fakerRequest())).thenReturn(ChargingDataRecordFaker.fakerResponse());

        //When
        ChargingDataRecord response = cdrController.create(ChargingDataRecordFaker.fakerRequest());

        //Then
        Mockito.verify(cdrService).createCdr(ChargingDataRecordFaker.fakerRequest());
        assertNotNull(response.id());
        assertEquals(response.endTime(), ChargingDataRecordFaker.fakerResponse().endTime());
        assertEquals(response.startTime(), ChargingDataRecordFaker.fakerResponse().startTime());
        assertEquals(response.sessionId(), ChargingDataRecordFaker.fakerResponse().sessionId());
        assertEquals(response.vehicleId(), ChargingDataRecordFaker.fakerResponse().vehicleId());
        assertEquals(response.totalAmount(), ChargingDataRecordFaker.fakerResponse().totalAmount());
    }

    @Test
    @DisplayName("Given cdr id  When calling controller Then service will get cdr with valid cdr id")
    public void getCdrByIdTest() {

        when(cdrService.getCdr(ChargingDataRecordFaker.fakerResponse().id())).thenReturn(ChargingDataRecordFaker.fakerResponse());

        //When
        ChargingDataRecord response = cdrController.getById(ChargingDataRecordFaker.fakerResponse().id());

        //Then
        Mockito.verify(cdrService).getCdr(ChargingDataRecordFaker.fakerResponse().id());
        assertEquals(response.id(), ChargingDataRecordFaker.fakerResponse().id());
    }

    @Test
    @DisplayName("Given VIN with soring order  When calling controller Then service will get list of cdr ")
    public void getAllCdrByVehicleIdTest() {

        when(cdrService.getAllCdrByVehicleId(ChargingDataRecordFaker.fakerRequest().vehicleId(),
                Sort.Direction.DESC,Sort.Direction.ASC)).thenReturn(List.of(ChargingDataRecordFaker.fakerResponse()));

        //When
        List<ChargingDataRecord> response = cdrController.getAllCdrByVehicleId(ChargingDataRecordFaker.fakerRequest().vehicleId(),
                Sort.Direction.DESC,Sort.Direction.ASC);

        //Then
        Mockito.verify(cdrService).getAllCdrByVehicleId(ChargingDataRecordFaker.fakerRequest().vehicleId(),
                Sort.Direction.DESC,Sort.Direction.ASC);
        assertEquals(response.get(0).id(), ChargingDataRecordFaker.fakerResponse().id());
    }

}
