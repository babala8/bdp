package com.zjft.microservice.treasurybrain.productcenter.po;

import lombok.Data;

@Data
public class ServiceStatusConvertPO {

	String id;

	Integer serviceNo;

	Integer curStatus;

	String operateType;

	String description;

	Integer moduleNo;

	Integer weakNode;

}
