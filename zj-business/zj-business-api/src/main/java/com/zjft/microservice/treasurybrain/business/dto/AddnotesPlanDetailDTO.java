package com.zjft.microservice.treasurybrain.business.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "加钞计划详情", description = "加钞计划详情")
public class AddnotesPlanDetailDTO extends DTO {

	@ApiModelProperty(value = "设备编号")
	private String devNo;
	private String dispatchNo;
	private Integer availableAmt;
	private String lastAddnoteDate;
	private String orgName;
	private String notAddCashDays;
	private String devCatalogName;
	private String address;
	private String keyEvent;
	private BigDecimal chsEstScore;
	private BigDecimal chsAuxScore;
	private String KeyEventDetail;
	private String lineNo;
	//加钞线路名称
//	private String lineName;

	//设备所属线路名称
	private String devLineName;
	private Integer cashReqAmt;
	private Integer planPredictAmt;
	private Integer planAddnotesAmt;
	private Integer sortNo;
	private BigDecimal useDays;
	private Integer status;
	private String taskNo;

}
