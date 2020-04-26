package com.zjft.microservice.treasurybrain.tauro.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 葛瑞莲
 * @since 2019/6/27
 */
@Data
@ApiModel(value = "手持机坐标信息", description = "手持机坐标信息")
public class TagReaderCoordsDTO extends DTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "手持机编号", example = "01021101")
	private String tagReaderNo;

	@ApiModelProperty(value = "日期--不用填，系统自取", example = "")
	private String rdDate;

	@ApiModelProperty(value = "时间--不用填，系统自取", example = "")
	private String rdTime;

	@ApiModelProperty(value = "使用人编号--不用填，系统自取", example = "")
	private String userNo;

	@ApiModelProperty(value = "经度", example = "50")
	private BigDecimal x;

	@ApiModelProperty(value = "纬度", example = "50")
	private BigDecimal y;

	@ApiModelProperty(value = "坐标获取方式", example = "0")
	private Integer coordsSrc;

	@ApiModelProperty(value = "关联任务类型", example = "0")
	private Integer taskType;

	@ApiModelProperty(value = "关联任务单编号", example = "JCRW201812130001")
	private String taskNo;

	@ApiModelProperty(value = "关联设备编号", example = "null")
	private String devNo;

	@ApiModelProperty(value = "交易代码", example = "1")
	private String transCode;

	@ApiModelProperty(value = "用户名称")
	private String userName;

}
