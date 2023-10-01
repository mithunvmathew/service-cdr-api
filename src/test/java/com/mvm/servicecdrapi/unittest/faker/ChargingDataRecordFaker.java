package com.mvm.servicecdrapi.unittest.faker;

import com.mvm.servicecdrapi.controller.dto.ChargingDataRecord;

import java.time.Instant;
import java.util.UUID;

public class ChargingDataRecordFaker {

    public static ChargingDataRecord fakerRequest() {
        return new ChargingDataRecord(null,
                "JT8BH68X8Y0026877",
                UUID.fromString("ce77083e-2d16-42e4-a5f5-63a620bc46fb"),
                Instant.parse("2023-08-20T18:46:19+02:00"),
                Instant.parse("2023-08-20T19:46:20+02:00"),
                100.10);
    }
    public static ChargingDataRecord fakerResponse() {
        return new ChargingDataRecord(
                UUID.fromString("8d09dbb5-9431-41a5-a64f-70c996c48e28"),
                "JT8BH68X8Y0026877",
                UUID.fromString("ce77083e-2d16-42e4-a5f5-63a620bc46fb"),
                Instant.parse("2023-08-20T18:46:19+02:00"),
                Instant.parse("2023-08-20T19:46:20+02:00"),
                100.10);
    }

    public static ChargingDataRecord fakerResponseWithDifferentId() {
        return new ChargingDataRecord(
                UUID.fromString("5e130b60-41f6-11ee-be56-0242ac120002"),
                "JT8BH68X8Y0026877",
                UUID.fromString("ce77083e-2d16-42e4-a5f5-63a620bc46fb"),
                Instant.parse("2023-08-20T18:46:19+02:00"),
                Instant.parse("2023-08-20T19:46:20+02:00"),
                100.10);
    }
    public static ChargingDataRecord fakerRequestWithHigherStartTime() {
        return new ChargingDataRecord(null,
                "JT8BH68X8Y0026877",
                UUID.fromString("ce77083e-2d16-42e4-a5f5-63a620bc46fb"),
                Instant.parse("2023-08-21T18:46:19+02:00"),
                Instant.parse("2023-08-21T19:46:20+02:00"),
                100.10);
    }

    public static ChargingDataRecord fakerRequestWithInvalidData() {
        return new ChargingDataRecord(null,
                "JT8BH68X8Y0026877",
                UUID.fromString("ce77083e-2d16-42e4-a5f5-63a620bc46fb"),
                Instant.parse("2023-08-21T18:46:19+02:00"),
                Instant.parse("2023-08-21T17:46:20+02:00"),
                100.10);
    }

}
