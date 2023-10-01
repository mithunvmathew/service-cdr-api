package com.mvm.servicecdrapi.service.mapper;

import com.mvm.servicecdrapi.controller.dto.ChargingDataRecord;
import com.mvm.servicecdrapi.repository.model.ChargingData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface ChargingDataMapper {


    @Mapping(target = "id", expression = "java(UUID.randomUUID())")
    ChargingData toEntity(ChargingDataRecord chargingDataRecord);


    ChargingDataRecord toDto(ChargingData chargingData);

    /*@Mapping(target = "startTime", expression = "java(chargingData.getStartTime.toInstant(ZoneOffset.UTC))")
    @Mapping(target = "endTime", expression = "java(chargingData.getEndTime.toInstant(ZoneOffset.UTC))")
    ChargingDataRecord toDto(ChargingData chargingData);*/

    /*@Named("instantToLocalDateTime")
    public LocalDateTime instantToLocalDateTime(Instant instant) {
        return LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
    }*/
    /*qualifiedByName = "instantToLocalDateTime"*/
   /*default LocalDateTime fromInstant(Instant instant) {
       return instant == null ? null : LocalDateTime.ofInstant(instant, ZoneOffset.UTC);
   }*/

}
