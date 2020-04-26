package com.zjft.microservice.treasurybrain.task.domain;

import com.zjft.microservice.treasurybrain.task.po.TaskEntityPO;
import lombok.Data;

import java.util.List;


@Data
public class TaskInfo {
    private String taskNo;

	private String addnotesPlanNo;

    private Integer taskType;

    private String planFinishDate;

    private Integer status;

    private String clrCenterNo;

	private String clrCenterName;

    private String lineNo;

	private String lineName;

    private String opNo1;

    private String opName1;

    private String opNo2;

    private String opName2;

    private String createOpNo;

	private String createOpName;

    private String createTime;

    private String note;

    private String modeOp;

    private String modeTime;

    private String modeNote;

    private String auditOp;

    private String auditTime;

    private String auditNote;

    private Integer planDistance;

    private Integer planTimeCost;

    private Integer urgentFlag;

	private String devNo;

	private String address;

	private String orgNo;

	private String orgName;

	private String shelfNo;

	private String customerNo;

	private String customerName;

	private Integer customerType;

	private List<TaskDetail> taskDetailList;

	private List<TaskEntityPO> taskEntityPOList;

}
