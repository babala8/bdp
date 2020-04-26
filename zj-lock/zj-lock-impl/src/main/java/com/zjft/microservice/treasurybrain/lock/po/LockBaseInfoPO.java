package com.zjft.microservice.treasurybrain.lock.po;

import lombok.Data;

/**
 *	锁具基本信息表
 * @author 韩通
 * @since 2019-06-26
 */
@Data
public class LockBaseInfoPO {

	private String lockCode;

	private String devNo;

	private String version;

	private String cversion;

	private Integer state;

	private String madeDate;

	private String installDate;

	private String note;

	private String blockNum;
}
