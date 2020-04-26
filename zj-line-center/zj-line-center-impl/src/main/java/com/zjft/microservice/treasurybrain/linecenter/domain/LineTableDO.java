package com.zjft.microservice.treasurybrain.linecenter.domain;

import com.zjft.microservice.treasurybrain.linecenter.po.LineTablePO;
import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/24 19:38
 */

@Data
public class LineTableDO {

	private String lineNo;

	private String lineName;

	private Integer addClrPeriod;

	private String note;

	private String clrCenterNo;

	private Integer lineType;

	private String clrCenterName;


}
