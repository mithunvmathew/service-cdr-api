package com.mvm.servicecdrapi.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mvm.servicecdrapi.controller.validation.ValidVIN;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.UUID;

public record ChargingDataRecord(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        UUID id,
        @Schema( type = "string", example = "1G2JB1245X7507311")
        @NotNull @ValidVIN String vehicleId,
        @NotNull UUID sessionId,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        @NotNull Instant startTime,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        @NotNull Instant endTime,
        @Schema( type = "float", example = "10")
        @NotNull Double totalAmount
) {
}

