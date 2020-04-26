package com.zjft.microservice.treasurybrain.channelcenter.domain;

import com.zjft.microservice.treasurybrain.channelcenter.po.BaseEntityInfoPO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 葛瑞莲
 * @since 2019/9/10
 */
@Data
public class BaseEntityInfoDO extends BaseEntityInfoPO {

	private String goodsName;

	private String customerName;

	private String customerTypeName;

}
