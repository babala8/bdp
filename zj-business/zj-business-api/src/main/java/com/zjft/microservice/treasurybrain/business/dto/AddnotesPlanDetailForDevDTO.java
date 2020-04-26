package com.zjft.microservice.treasurybrain.business.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Data;

/**
 * 查询系统选择出的计划加钞设备列表
 * 包含余钞量、日均取款量、剩余可用天数、距上次加钞天数等
 * 仅用于设备选择页面、金额预测页面
 */
@Data
public class AddnotesPlanDetailForDevDTO extends DTO {

	private static final long serialVersionUID = 1L;

	/**
	 * 设备号
	 */
	private String devNo;
	/**
	 * 设备类型名称
	 */
	private String devCatalogName;
	/**
	 * 设备地址
	 */
	private String address;
	/**
	 * 决定型事件
	 */
	private String keyEvent;
	/**
	 * 预测型分值
	 */
	private Long chsEstScore;
	/**
	 * 辅助型分值
	 */
	private Long chsAuxScore;
	/**
	 * 所属机构名称
	 */
	private String orgName;
	/**
	 * 剩余可取钞量（元）
	 */
	private Integer availableAmt;
	/**
	 * 日均取款量（元）
	 */
	private Integer dayAvgAmt;

	/**
	 * 日均存款量（元）
	 */
	private Integer dayAvgDep;

	/**
	 * 剩余钞量可用天数
	 */
	private Integer useDays;
	/**
	 * 距上次加钞时长(天)
	 */
	private Integer notAddCashDays;
	/**
	 * 决定型事件详细描述
	 */
	private String keyEventDetail;
	/**
	 * 计划加钞金额
	 */
	private Integer planAddnotesAmt;

	/**
	 * 所属机构分组编号
	 */
	private String lineNo;
	/**
	 * 线路名称
	 */
	private String addNotesLineName;

	/**
	 * 设备最大装钞量
	 */
	private Integer devStantardSize;

}
