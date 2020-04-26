package com.zjft.microservice.treasurybrain.linecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/24 16:19
 */
@Data
@ApiModel
public class AddCallCustomerLineRunDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "金库编号",name = "金库编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "年月",name = "年月",required = true)
	private String theYearMonth;

	@ApiModelProperty(value = "线路编号列表",name = "线路编号列表")
	private List<String> lineNos;
}

