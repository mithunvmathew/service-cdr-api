package com.mvm.servicecdrapi.integrationtest;

public class TestDataProvider {

    public static String getCdrRequest1() {
        return "{\n" +
                "    \"vehicleId\": \"1FAFP10P4WW262978\",\n" +
                "    \"sessionId\": \"4cec64a2-323b-4e7e-a0f1-a0bd22750741\",\n" +
                "    \"startTime\": \"2023-08-15T04:46:19+02:00\",\n" +
                "    \"endTime\": \"2023-08-15T05:46:19+02:00\",\n" +
                "    \"totalAmount\": 40\n" +
                "}";
    }

    public static String getCdrRequest2() {
        return "{\n" +
                "    \"vehicleId\": \"1FAFP10P4WW262978\",\n" +
                "    \"sessionId\": \"4cec64a2-323b-4e7e-a0f1-a0bd22750741\",\n" +
                "    \"startTime\": \"2023-08-15T10:46:19+02:00\",\n" +
                "    \"endTime\": \"2023-08-15T11:30:19+02:00\",\n" +
                "    \"totalAmount\": 35\n" +
                "}";
    }

    public static String getCdrRequest3() {
        return "{\n" +
                "    \"vehicleId\": \"1FAFP10P4WW262978\",\n" +
                "    \"sessionId\": \"4cec64a2-323b-4e7e-a0f1-a0bd22750741\",\n" +
                "    \"startTime\": \"2023-08-16T01:46:19+02:00\",\n" +
                "    \"endTime\": \"2023-08-17T11:30:19+02:00\",\n" +
                "    \"totalAmount\": 135\n" +
                "}";
    }

    public static String getCdrRequestWithInvalidData() {
        return "{\n" +
                "    \"vehicleId\": \"1FAFP10P4WW262978\",\n" +
                "    \"sessionId\": \"4cec64a2-323b-4e7e-a0f1-a0bd22750741\",\n" +
                "    \"startTime\": \"2023-08-15T04:46:19+02:00\",\n" +
                "    \"endTime\": \"2023-08-15T04:46:19+02:00\",\n" +
                "    \"totalAmount\": 40\n" +
                "}";
    }

    public static String getCdrRequestWithInvalidVehicleId() {
        return "{\n" +
                "    \"vehicleId\": \"1FAFP10P4\",\n" +
                "    \"sessionId\": \"4cec64a2-323b-4e7e-a0f1-a0bd22750741\",\n" +
                "    \"startTime\": \"2023-08-16T01:46:19+02:00\",\n" +
                "    \"endTime\": \"2023-08-17T11:30:19+02:00\",\n" +
                "    \"totalAmount\": 135\n" +
                "}";
    }
}
