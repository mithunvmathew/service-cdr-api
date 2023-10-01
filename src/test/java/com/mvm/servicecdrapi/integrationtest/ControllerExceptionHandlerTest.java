package com.mvm.servicecdrapi.integrationtest;

import com.mvm.servicecdrapi.controller.CdrController;
import com.mvm.servicecdrapi.controller.ControllerExceptionHandler;
import com.mvm.servicecdrapi.exception.InvalidVinException;
import com.mvm.servicecdrapi.exception.RecordNotFoundException;
import com.mvm.servicecdrapi.integrationtest.BaseIntegrationTest;
import com.mvm.servicecdrapi.unittest.faker.ChargingDataRecordFaker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
public class ControllerExceptionHandlerTest extends BaseIntegrationTest {
    private MockMvc mvc;

    @Mock
    private CdrController cdrController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(cdrController)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }


    @Test
    public void recordNotFoundException() throws Exception {
        doThrow(RecordNotFoundException.class).when(cdrController).getById(UUID.fromString("2bee24de-41ed-11ee-be56-0242ac120002"));
        mvc.perform(get("/2bee24de-41ed-11ee-be56-0242ac120002"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void invalidVINException() throws Exception {
        doThrow(InvalidVinException.class).when(cdrController).create(ChargingDataRecordFaker.fakerRequest());
        mvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequestWithInvalidVehicleId()))
                .andExpect(status().isBadRequest());
    }
}
