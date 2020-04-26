package com.zjft.microservice.treasurybrain.timejob.po;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TimeJobTaskEntityPO {

	private String id;

	private String taskNo;

	private String entityNo;

	private String productNo;

	private Integer direction;

	private String parentEntity;

	private Integer leafFlag;

	private Integer status;

	private String note;

	private String customerNo;

	private Integer depositType;

	private BigDecimal amount;

	List<TimeJobTaskEntityDetailPO> taskEntityDetailDTOList;


}
