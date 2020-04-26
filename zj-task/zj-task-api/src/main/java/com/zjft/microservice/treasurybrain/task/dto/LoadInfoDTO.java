package com.zjft.microservice.treasurybrain.task.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 周昊
 * @since 2019-08-13
 */
@Data
public class LoadInfoDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String taskNo;

	private String planFinishDate;

	private String lineName;

	private String centerName;

	private Integer status;

	private Integer containerType;

	//任务单对应的所有设备总金额
	private double allocAmount;

	//任务单清点总金额
	private BigDecimal checkAmount;

	//任务单加钞金额
	private double addnoteAmount;

	private int customerCount;

	private String createOpName;

	private String orgName;
}
