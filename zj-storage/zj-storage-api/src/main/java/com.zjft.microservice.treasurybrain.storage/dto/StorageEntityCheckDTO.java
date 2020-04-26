package com.zjft.microservice.treasurybrain.storage.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 韩通
 * @since 2019/11/25
 */
@Data
@ApiModel(description = "门禁审核信息",value = "门禁审核信息")
public class StorageEntityCheckDTO {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(name = "类型")
	private String type;

	@ApiModelProperty(name = "实物信息【容器编号列表】")
	private List<String> goods;


}
