package com.zjft.microservice.treasurybrain.task.domain;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/6 14:40
 */
@Data
public class TaskDetailToExportDO extends TaskDetail {

	private String keyEventDetail;

	private String addnotesMode;

	private String opNo1;

	private String opName1;


	private String opNo2;


	private String opName2;
}
