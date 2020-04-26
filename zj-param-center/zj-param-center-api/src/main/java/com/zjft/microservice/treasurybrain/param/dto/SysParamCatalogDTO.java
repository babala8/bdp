package com.zjft.microservice.treasurybrain.param.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 杨光
 */
@Getter
@Setter
@ToString
public class SysParamCatalogDTO extends DTO {

	private Integer catalog;

	private String catalogName;

	private String description;


}
