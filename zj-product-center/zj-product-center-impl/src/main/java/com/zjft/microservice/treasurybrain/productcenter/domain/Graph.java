package com.zjft.microservice.treasurybrain.productcenter.domain;

import lombok.Data;

@Data
public class Graph {
	private Node[] nodeList;
	private int vertex_num;
	private int edge_num;
}
