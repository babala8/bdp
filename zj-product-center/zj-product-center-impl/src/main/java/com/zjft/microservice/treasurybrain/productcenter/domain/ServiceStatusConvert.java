package com.zjft.microservice.treasurybrain.productcenter.domain;

import lombok.Data;

@Data
public class ServiceStatusConvert {
	Integer curStatus;
	String operateType;
	Integer nextStatus;
	Integer weakNode;
}
