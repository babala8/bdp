package com.zjft.microservice.treasurybrain.channelcenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 崔耀中
 * @since 2019-11-19
 */
@Data
@ApiModel(value = "网点营业时间信息", description = "网点营业时间信息")
public class OrgBusinessTimeDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "机构编号", example = "0440200801")
	private String orgNo;

	@ApiModelProperty(value = "营业时段(1-上午,2-下午)", example = "1")
	private String orgTimeInterval;

	@ApiModelProperty(value = "开始时间(hh:mm:ss)", example = "10:00:00")
	private String openTime;

	@ApiModelProperty(value = "结束时间(hh:mm:ss)", example = "10:00:00")
	private String closeTime;

	@ApiModelProperty(value = "日期：1-周一，2-周二", example = "1")
	private String orgDay;

	@ApiModelProperty(value = "营业时间列表")
	private List<OrgBusinessTimeDTO> businessTimeList;

}
