package com.mvm.servicecdrapi.integrationtest;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class ServiceCdrApiApplicationTests extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    final ObjectMapper mapper = new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT);

    @Test
    @Sql("classpath:sql/clean-tables.sql")
    @DisplayName("Given cdr request json  When making request Then cdr stored in db with time in UTC and will get the data back with cdr id")
    void createCdrTest() throws Exception {

        MvcResult result = mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest1())
                ).andExpect(status().isOk())
                .andReturn();
        Map<String, Object> response = mapper.readValue(result.getResponse().getContentAsString(), HashMap.class);
        assertNotNull(response.get("id"));
        assertEquals(response.get("startTime"), "2023-08-15T02:46:19Z");
        assertEquals(response.get("endTime"), "2023-08-15T03:46:19Z");
    }

    @Test
    @Sql("classpath:sql/clean-tables.sql")
    @DisplayName("Given cdr request json with old data  When making request Then will get InvalidDataException")
    void createCdrWithOldTest() throws Exception {

        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest1())
                ).andExpect(status().isOk())
                .andReturn();

        MvcResult result = mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest1())
                ).andExpect(status().isBadRequest())
                .andReturn();
        Map<String, Object> response = mapper.readValue(result.getResponse().getContentAsString(), HashMap.class);
        assertEquals(response.get("code"), "BadRequestException");
    }

    @Test
    @Sql("classpath:sql/clean-tables.sql")
    @DisplayName("Given cdr request json with startTime and endTime equal When making request Then will get InvalidDataException")
    void createCdrWithInvalidDataTest() throws Exception {

        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequestWithInvalidData())
                ).andExpect(status().isBadRequest());
    }

    @Test
    @Sql("classpath:sql/clean-tables.sql")
    @DisplayName("Given cdr id When making request to getCdrById Then will get requested valid data")
    void getCdrByIdTest() throws Exception {

        MvcResult result = mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest1())
                ).andExpect(status().isOk())
                .andReturn();
        Map<String, Object> response = mapper.readValue(result.getResponse().getContentAsString(), HashMap.class);
        String id = response.get("id").toString();
        MvcResult getResult = mockMvc.perform(get("/cdr/"+id).contentType(APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        Map<String, Object> getResponse = mapper.readValue(getResult.getResponse().getContentAsString(), HashMap.class);
        assertEquals(getResponse.get("id"), response.get("id"));
    }

    @Test
    @Sql("classpath:sql/clean-tables.sql")
    @DisplayName("Given vehicleId  When making request to get all cdr Then will get sorted by default(ASC)")
    void getAllCdrByVehicleIdTest() throws Exception {

        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest1())
                ).andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest2())
                ).andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest3())
                ).andExpect(status().isOk())
                .andReturn();

        MvcResult getResult = mockMvc.perform(get("/cdr/search/1FAFP10P4WW262978").contentType(APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        List<Map<String, Object>> resultList = mapper.readValue(getResult.getResponse().getContentAsString(), new TypeReference<List<Map<String, Object>>>(){});

        assertEquals(3, resultList.size());
        assertEquals("2023-08-15T02:46:19Z", resultList.get(0).get("startTime"));
    }

    @Test
    @Sql("classpath:sql/clean-tables.sql")
    @DisplayName("Given vehicleId  When making request to get all cdr Then will get sorted by DESC)")
    void getAllCdrByVehicleIdSortedinDescTest() throws Exception {

        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest1())
                ).andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest2())
                ).andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest3())
                ).andExpect(status().isOk())
                .andReturn();

        MvcResult getResult = mockMvc.perform(get("/cdr/search/1FAFP10P4WW262978?sortOrderByStartTime=DESC&sortOrderByEndTime=DESC").contentType(APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        List<Map<String, Object>> resultList = mapper.readValue(getResult.getResponse().getContentAsString(), new TypeReference<List<Map<String, Object>>>(){});

        assertEquals(3, resultList.size());
        assertEquals("2023-08-15T23:46:19Z", resultList.get(0).get("startTime"));
    }

    @Test
    @Sql("classpath:sql/clean-tables.sql")
    @DisplayName("Given vehicleId  When making request to get all cdr with sortOrderByStartTime in Asc and sortOrderByEndTime in Desc Then will get sorted data)")
    void getAllCdrByVehicleIdSortedinWithStartTimeInAscAndEndTimeInDescTest() throws Exception {

        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest1())
                ).andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest2())
                ).andExpect(status().isOk())
                .andReturn();
        mockMvc.perform(post("/cdr").contentType(APPLICATION_JSON).content(TestDataProvider.getCdrRequest3())
                ).andExpect(status().isOk())
                .andReturn();

        MvcResult getResult = mockMvc.perform(get("/cdr/search/1FAFP10P4WW262978?sortOrderByStartTime=DESC&sortOrderByEndTime=ASC").contentType(APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn();
        List<Map<String, Object>> resultList = mapper.readValue(getResult.getResponse().getContentAsString(), new TypeReference<List<Map<String, Object>>>(){});

        assertEquals(3, resultList.size());
        assertEquals("2023-08-15T23:46:19Z", resultList.get(0).get("startTime"));
    }


}
