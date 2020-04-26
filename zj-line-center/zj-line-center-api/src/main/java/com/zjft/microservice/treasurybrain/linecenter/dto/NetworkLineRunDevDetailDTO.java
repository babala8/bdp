package com.zjft.microservice.treasurybrain.linecenter.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 网点线路运行图网点信息接口类
 *
 * @author zhangjs
 * @since 2019/8/28 18:51
 */
@Data
@ApiModel(value = "网点线路运行图网点信息", description = "网点线路运行图网点信息")
public class NetworkLineRunDevDetailDTO {

	@ApiModelProperty(value = "网点号")
	private String networkNo;

	@ApiModelProperty(value = "网点线路运行编号")
	private String networkLineRunNo;

	@ApiModelProperty(value = "网点名称")
    private String networkName;

	@ApiModelProperty(value = "顺序号")
    private Integer sort;

}
