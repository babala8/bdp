package com.zjft.microservice.treasurybrain.lock.domain;

import com.zjft.microservice.treasurybrain.lock.po.LockTransTablePO;
import lombok.Data;

/**
 *	锁具日志信息
 * @author 韩通
 * @since 2019-06-26
 */
@Data
public class LockTransTableDO extends LockTransTablePO {
	/**
	 * 交易开始时间
	 */
	private String tranStartDate;

	/**
	 * 交易结束时间
	 */
	private String tranEndDate;
}
