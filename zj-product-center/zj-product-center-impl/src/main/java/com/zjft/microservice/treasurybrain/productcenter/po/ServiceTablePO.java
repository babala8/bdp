package com.zjft.microservice.treasurybrain.productcenter.po;

import lombok.Data;

@Data
public class ServiceTablePO {

	Integer serviceNo;

	String serviceName;

	Integer customerType;

	String note;

	String createTime;

	String updateTime;

	Integer status;

	Integer type;

}
