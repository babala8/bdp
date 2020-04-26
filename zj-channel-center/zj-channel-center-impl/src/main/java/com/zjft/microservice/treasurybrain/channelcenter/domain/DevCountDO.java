package com.zjft.microservice.treasurybrain.channelcenter.domain;

import com.zjft.microservice.treasurybrain.channelcenter.po.DevCountPO;
import lombok.Data;

import java.util.List;

/**
 * @author 崔耀中
 * @since 2019-10-10
 */
@Data
public class DevCountDO extends DevCountPO {

	/**
	 * 设备批次
	 */
	private Integer batch;

	/**
	 * @Description 清机中心名称
	 * @Param
	 */
	private String centerName;

	/**
	 * @Description 正在执行任务数量
	 * @Param
	 */
	private Integer countNum;

	private List<CountTaskInfoDO> doingList;

	private List<CountTaskInfoDO> queueList;

	private List<CountTaskInfoDO> doneList;

}
