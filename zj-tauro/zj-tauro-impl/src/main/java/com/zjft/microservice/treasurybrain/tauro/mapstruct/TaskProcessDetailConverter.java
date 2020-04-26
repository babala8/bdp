package com.zjft.microservice.treasurybrain.tauro.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskProcessDetailConverter {

	TaskProcessDetailConverter INSTANCE = Mappers.getMapper(TaskProcessDetailConverter.class);


}
