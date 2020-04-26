package com.zjft.microservice.treasurybrain.lock.po;

import lombok.Data;

/**
 *	锁具状态表
 * @author 韩通
 * @since 2019-06-26
 */
@Data
public class LockStatusTablePO {

	private String lockCode;

	private String status;

	private String updateTime;
}
