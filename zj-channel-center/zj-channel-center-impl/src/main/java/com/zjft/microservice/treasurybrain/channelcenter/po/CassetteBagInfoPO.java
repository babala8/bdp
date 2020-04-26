package com.zjft.microservice.treasurybrain.channelcenter.po;

import lombok.Data;

/**
 * 钞袋信息
 *
 * @author liuyuan
 * @since 2019/6/13 08:46
 */

@Data
public class CassetteBagInfoPO {

	//钞箱袋管理编号
	private String bagNo;

	//钞箱袋银行编号
	private String bagNoBank;

	//钞箱袋序列号
	private String bagSerial;

	//标签编号
	private String tagTid;

	//钞箱袋品牌
	private int bagVendor;

	//钞箱袋规格
	private int bagSize;

	//状态
	private int status;
}
