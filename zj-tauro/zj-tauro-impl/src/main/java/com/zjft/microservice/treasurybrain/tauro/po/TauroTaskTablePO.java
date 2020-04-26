package com.zjft.microservice.treasurybrain.tauro.po;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 常 健
 * @since 2019/08/08
 */
@Data
public class TauroTaskTablePO {
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

	private String type;
}
