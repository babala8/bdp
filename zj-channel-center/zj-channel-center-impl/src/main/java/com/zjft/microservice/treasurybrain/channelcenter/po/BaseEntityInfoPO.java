package com.zjft.microservice.treasurybrain.channelcenter.po;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 葛瑞莲
 * @since 2019/9/10
 */
@Data
public class BaseEntityInfoPO {

	private String entityNo;

	private String goodsNo;

	private String prams;

	private String customerNo;

	private Integer customerType;

	private String createTime;

	private String modTime;

}
