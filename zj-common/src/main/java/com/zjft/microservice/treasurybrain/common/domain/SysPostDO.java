package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

/**
 * @author 常 健
 * @since 2019/11/12
 */
@Data
public class SysPostDO {

	private String postNo;

	private String postName;

	private Integer postType;

	private String note;
}
