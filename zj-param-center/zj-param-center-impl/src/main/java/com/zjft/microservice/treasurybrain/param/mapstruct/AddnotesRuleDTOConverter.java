package com.zjft.microservice.treasurybrain.param.mapstruct;

import com.zjft.microservice.treasurybrain.param.domain.AddnotesRule;
import com.zjft.microservice.treasurybrain.param.dto.AddnotesRuleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddnotesRuleDTOConverter {

	AddnotesRuleDTOConverter INSTANCE= Mappers.getMapper(AddnotesRuleDTOConverter.class);
	@Mappings({
			@Mapping(source = "ruleId",target = "ruleId"),
			@Mapping(source = "ruleGenOpName", target = "ruleGenOpName"),
			@Mapping(source = "ruleGenOp",target = "ruleGenOpNo")
	})
	AddnotesRuleDTO domain2dto(AddnotesRule addnotesRule);

}
