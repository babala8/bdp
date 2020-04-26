package com.zjft.microservice.treasurybrain.linecenter.po;

import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/23 16:01
 */
@Data
public class LineSchedulePO {

	private String customerNo;

	private String lineWorkId;

	private String arrivalTime;

	private String clrTimeInterval;

}
