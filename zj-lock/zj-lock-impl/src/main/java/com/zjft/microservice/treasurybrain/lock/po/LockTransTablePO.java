package com.zjft.microservice.treasurybrain.lock.po;

import lombok.Data;

/**
 *	锁具交易记录表
 * @author 韩通
 * @since 2019-06-26
 */
@Data
public class LockTransTablePO {

	private String serialNo;

	private String devNo;

	private String lockCode;

	private String tranDate;

	private String tranTime;

	private String tranType;

	private String encryptCode;

	private String esbCode;

	private String retCode;

	private String retMsg;
}
