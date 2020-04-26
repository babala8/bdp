package com.zjft.microservice.treasurybrain.productcenter.domain;

import lombok.Data;

@Data
public class EdgeNode {
	Integer index;
	String operateType;
	Integer weakNode;
	EdgeNode edgeNode;
}
