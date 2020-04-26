package com.zjft.microservice.treasurybrain.usercenter.domain;

import com.zjft.microservice.treasurybrain.usercenter.po.PostScheduleMouldPO;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/19
 */
@Data
public class PostScheduleDetailDO extends PostScheduleMouldPO implements Comparable<PostScheduleDetailDO>{

	/*
	 *生成排班信息用
	 *
	 */
	private List<PostScheduleClassDetailDO> classesList;

	private Integer countNo;

	@Override
	public int compareTo(PostScheduleDetailDO o) {
		return this.countNo-o.getCountNo();
	}
}
