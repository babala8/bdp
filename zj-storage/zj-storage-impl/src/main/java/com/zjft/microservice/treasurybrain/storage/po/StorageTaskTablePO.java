package com.zjft.microservice.treasurybrain.storage.po;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StorageTaskTablePO {
    private String taskNo;

    private Integer taskType;

    private String planFinishDate;

    private Integer status;

    private String clrCenterNo;

    private String carNumber;

    private String lineNo;

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

    private BigDecimal planDistance;

    private Integer planTimeCost;

    private Integer urgentFlag;

	private String shelfNo;
}
