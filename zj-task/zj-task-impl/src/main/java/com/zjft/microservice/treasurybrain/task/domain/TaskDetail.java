package com.zjft.microservice.treasurybrain.task.domain;

import com.zjft.microservice.treasurybrain.task.po.TaskEntityPO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TaskDetail  {
    private String id;

    private String taskNo;

    private String productNo;

	private Integer  direction;

	private BigDecimal amount;

	private List<TaskEntityPO> taskEntityPOList;

	private List<TaskDetailPropertyDO> detailList;

}
