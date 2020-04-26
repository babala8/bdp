package com.zjft.microservice.treasurybrain.managecenter.domain;

import com.zjft.microservice.treasurybrain.managecenter.po.BaseEntityInfoPO;
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
