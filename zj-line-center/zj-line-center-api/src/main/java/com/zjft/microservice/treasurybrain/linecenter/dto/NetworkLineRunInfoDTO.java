package com.zjft.microservice.treasurybrain.linecenter.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 网点线路运行图信息接口类
 *
 * @author zhangjs
 * @since 2019/8/28 18:51
 */
@Data
@ApiModel(value = "网点线路运行图信息", description = "网点线路运行图信息")
public class NetworkLineRunInfoDTO extends DTO {

	@ApiModelProperty(value = "清机中心编号")
	private String clrCenterNo;

	@ApiModelProperty(value = "网点线路运行编号")
	private String networkLineRunNo;

	@ApiModelProperty(value = "网点线路编号")
	private String networkLineNo;

	@ApiModelProperty(value = "网点线路名称")
	private String networkLineName;

	@ApiModelProperty(value = "年月[yyyy-mm]")
	private String theYearMonth;

	@ApiModelProperty(value = "日")
	private String theDay;

	@ApiModelProperty(value = "网点数量", example = "0")
	private Integer networkCount;

	@ApiModelProperty(value = "网点列表")
	private List<NetworkLineRunDevDetailDTO> networkList;

}
