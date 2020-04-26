package com.zjft.microservice.treasurybrain.channelcenter.domain;

import com.zjft.microservice.treasurybrain.channelcenter.po.CassetteInfoPO;
import lombok.Data;


/**
 * @author liuyuan
 * @since 2019/6/14 09:55
 */

@Data
public class CassetteInfoDO extends CassetteInfoPO {

	/**
	 * 钞箱品牌名称
	 * DEV_VENDOR_TABLE 设备品牌表
	 */
	private String cassetteVendorName;

}
