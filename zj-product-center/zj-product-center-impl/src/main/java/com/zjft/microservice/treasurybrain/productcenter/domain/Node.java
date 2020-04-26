package com.zjft.microservice.treasurybrain.productcenter.domain;

import lombok.Data;

@Data
public class Node {
	Integer nodeName;
	String operateType;
	EdgeNode first_edge = new EdgeNode();
}
