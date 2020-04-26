package com.zjft.microservice.treasurybrain.linecenter.po;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/23 15:58
 */
@Data
public class LineWorkPO {

	private String lineWorkId;

	private String lineNo;

	private String theYearMonth;

	private String theDay;

	private int customerCount;

}
