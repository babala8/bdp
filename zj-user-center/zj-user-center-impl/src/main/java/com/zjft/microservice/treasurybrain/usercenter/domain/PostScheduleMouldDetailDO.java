package com.zjft.microservice.treasurybrain.usercenter.domain;

import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/18
 */
@Data
public class PostScheduleMouldDetailDO {

	private String classesNo;

	private String classesName;

	private Integer countNo;

	private List<PostScheduleMouldOpDO> opList;
}
