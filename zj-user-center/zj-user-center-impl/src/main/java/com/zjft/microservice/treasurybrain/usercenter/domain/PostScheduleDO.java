package com.zjft.microservice.treasurybrain.usercenter.domain;

import com.zjft.microservice.treasurybrain.usercenter.po.PostSchedulePO;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/20
 * 排班信息分页查询用
 */
@Data
public class PostScheduleDO extends PostSchedulePO {

	private String orgName;

	private String postName;

	private String mouldName;

	private List<PostSchedulePlanDO> detailList;
}
