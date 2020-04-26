package com.zjft.microservice.treasurybrain.business.domain;

import lombok.Data;

/**
 * @author yt
 * @since 2013-05-14 用于设备选择列表展示页面
 */
@Data
public class AddnotesPlanDetailForDev {

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
	private Integer availableDays;
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

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}

	public String getDevNo() {
		return devNo;
	}

	public void setDevNo(String devNo) {
		this.devNo = devNo;
	}

	public String getDevCatalogName() {
		return devCatalogName;
	}

	public void setDevCatalogName(String devCatalogName) {
		this.devCatalogName = devCatalogName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getKeyEvent() {
		return keyEvent;
	}

	public void setKeyEvent(String keyEvent) {
		this.keyEvent = keyEvent;
	}

	public Long getChsEstScore() {
		return chsEstScore;
	}

	public void setChsEstScore(Long chsEstScore) {
		this.chsEstScore = chsEstScore;
	}

	public Long getChsAuxScore() {
		return chsAuxScore;
	}

	public void setChsAuxScore(Long chsAuxScore) {
		this.chsAuxScore = chsAuxScore;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getAvailableAmt() {
		return availableAmt;
	}

	public void setAvailableAmt(Integer availableAmt) {
		this.availableAmt = availableAmt;
	}

	public Integer getDayAvgAmt() {
		return dayAvgAmt;
	}

	public void setDayAvgAmt(Integer dayAvgAmt) {
		this.dayAvgAmt = dayAvgAmt;
	}

	public Integer getAvailableDays() {
		return availableDays;
	}

	public void setAvailableDays(Integer availableDays) {
		this.availableDays = availableDays;
	}

	public Integer getNotAddCashDays() {
		return notAddCashDays;
	}

	public void setNotAddCashDays(Integer notAddCashDays) {
		this.notAddCashDays = notAddCashDays;
	}

	public String getKeyEventDetail() {
		return keyEventDetail;
	}

	public void setKeyEventDetail(String keyEventDetail) {
		this.keyEventDetail = keyEventDetail;
	}

	public Integer getPlanAddnotesAmt() {
		return planAddnotesAmt;
	}

	public void setPlanAddnotesAmt(Integer planAddnotesAmt) {
		this.planAddnotesAmt = planAddnotesAmt;
	}

	public String getAddNotesLineName() {
		return addNotesLineName;
	}

	public void setAddNotesLineName(String addNotesLineName) {
		this.addNotesLineName = addNotesLineName;
	}

	public Integer getDevStantardSize() {
		return devStantardSize;
	}

	public void setDevStantardSize(Integer devStantardSize) {
		this.devStantardSize = devStantardSize;
	}
	public Integer getDayAvgDep() {
		return dayAvgDep;
	}
	public void setDayAvgDep(Integer dayAvgDep) {
		this.dayAvgDep = dayAvgDep;
	}
}
