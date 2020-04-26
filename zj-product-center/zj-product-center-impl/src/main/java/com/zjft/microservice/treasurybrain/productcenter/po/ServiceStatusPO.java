package com.zjft.microservice.treasurybrain.productcenter.po;

import lombok.Data;

@Data
public class ServiceStatusPO {

	Integer serviceNo;

	Integer status;

	String name;

	String note;

	Integer weakNode;

	String operateType;

}
