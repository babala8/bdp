package com.zjft.microservice.treasurybrain.task.domain;

import lombok.Data;

@Data
public class TaskDetailPropertyDO {
    private String id;

    private String detailId;

    private String key;

    private String name;

    private String value;

	private String taskNo;

}
