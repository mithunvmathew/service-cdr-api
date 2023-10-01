package com.mvm.servicecdrapi.controller;

import com.mvm.servicecdrapi.controller.dto.ChargingDataRecord;
import com.mvm.servicecdrapi.controller.validation.ValidVIN;
import com.mvm.servicecdrapi.service.CdrService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Validated
@RestController
@RequestMapping("/cdr")
public class CdrController {
    private final CdrService cdrService;

    public CdrController(CdrService cdrService) {
        this.cdrService = cdrService;
    }

    @Operation(summary = "create cdr record", description = "This endpoint create new cdr record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema =
                    @Schema(implementation = ChargingDataRecord.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request Exception", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ControllerExceptionHandler.Error.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ControllerExceptionHandler.Error.class))})})
    @PostMapping
    public ChargingDataRecord create(@Valid @RequestBody ChargingDataRecord chargingDataRecord) {
        return cdrService.createCdr(chargingDataRecord);
    }

    @Operation(summary = "Get cdr by id", description = "This endpoint get the cdr record by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ChargingDataRecord.class))}),
            @ApiResponse(responseCode = "400", description = "Bad Request Exception", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ControllerExceptionHandler.Error.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ControllerExceptionHandler.Error.class))})})
    @GetMapping("/{cdr_id}")
    public ChargingDataRecord getById(@PathVariable("cdr_id") @NotNull UUID id) {
        return cdrService.getCdr(id);
    }

    @Operation(summary = "Find all cdr by Vehicle Id", description = "This endpoint get all cdr by Vehicle Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok", content =
                    {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ChargingDataRecord.class)))}),
            @ApiResponse(responseCode = "400", description = "Bad Request Exception", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ControllerExceptionHandler.Error.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ControllerExceptionHandler.Error.class))})})
    @GetMapping("/search/{vehicle_id}")
    public List<ChargingDataRecord> getAllCdrByVehicleId(@PathVariable("vehicle_id") @NotBlank  String id,
                                                         @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortOrderByStartTime,
                                                         @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortOrderByEndTime) {
        return cdrService.getAllCdrByVehicleId(id, sortOrderByStartTime, sortOrderByEndTime);
    }
}
