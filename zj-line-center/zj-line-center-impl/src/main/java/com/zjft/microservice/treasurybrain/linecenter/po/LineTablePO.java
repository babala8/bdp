package com.zjft.microservice.treasurybrain.linecenter.po;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/24 19:35
 */
@Data
public class LineTablePO {

	private String lineNo;

	private String lineName;

	private Integer addClrPeriod;

	private String note;

	private String clrCenterNo;

	private Integer lineType;

}
