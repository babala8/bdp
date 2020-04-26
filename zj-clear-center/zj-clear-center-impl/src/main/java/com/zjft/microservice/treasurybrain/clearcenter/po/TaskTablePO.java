package com.zjft.microservice.treasurybrain.clearcenter.po;

import lombok.Data;

import java.util.List;

@Data
public class TaskTablePO {
    private String taskNo;

    private Integer taskType;

    private String planFinishDate;

    private Integer status;

    private String clrCenterNo;

    private String carNumber;

    private String lineNo;

    private String lineName;

    private String opNo1;

    private String opNo2;

    private String createOpNo;

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

    private String addnotesPlanNo;

	List<TaskCheckPO> taskCheckList;
}
