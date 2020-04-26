package com.zjft.microservice.treasurybrain.common.util;

/**
 * echarts图表类型枚举
 */
public enum ChartTypeEnum {

	/**
	 * 一般图表类型，需要组装数据
	 */
	ECHARTS_GRAPH("0"),
	
	/**
	 * 数据不组装
	 */
	INFO_VIEW("1"),
	
	/**
	 * 数据不组装
	 */
	DETAIL_VIEW("2"),
	
	/**
	 * 数据不组装
	 */
	RANK_VIEW("3"),
	
	
	
	TRUN_OVER("10"),
	
	FLIP_CARD("11"),
	
	CAROUSEL("12");
	
	
	private String chartType;

	private ChartTypeEnum(String chartType) {
		this.chartType = chartType;
	}

	public String getChartType() {
		return this.chartType;
	}

}
