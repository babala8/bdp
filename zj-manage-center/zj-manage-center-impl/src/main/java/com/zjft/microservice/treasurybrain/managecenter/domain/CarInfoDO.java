package com.zjft.microservice.treasurybrain.managecenter.domain;

import com.zjft.microservice.treasurybrain.managecenter.po.CarInfoPO;
import lombok.Data;

/**
 * @author 崔耀中
 * @since 2019-09-21
 */
@Data
public class CarInfoDO extends CarInfoPO {

	/**
	 * 押运公司名称
	 */
	private String companyName;
}
