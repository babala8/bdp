package com.zjft.microservice.treasurybrain.usercenter.domain;

import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/21
 */
@Data
public class PostSchedulePlanDO {

	private String planDate;

	private String classesNo;

	private String classesName;

	private List<PostScheduleMouldOpDO> opList;
}
