package com.zjft.microservice.treasurybrain.datainsight.domain;

import lombok.Data;

/**
 * @author 杨光
 */
@Data
public class InsightUserConfigDO {
	private static final long serialVersionUID = 1L;

	private String username;
	private String userDefaultView;
	private Integer subjectId;
}
