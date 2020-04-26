package com.zjft.microservice.treasurybrain.datainsight.mapstruct;

import com.zjft.microservice.treasurybrain.datainsight.domain.ChartSubject;
import com.zjft.microservice.treasurybrain.datainsight.dto.SubjectDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author 杨光
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChartSubjectConverter {

	ChartSubjectConverter INSTANCE = Mappers.getMapper(ChartSubjectConverter.class);

	@Mappings({

	})
	List<SubjectDTO> subjectToDto(List<ChartSubject> chartSubject);


}

