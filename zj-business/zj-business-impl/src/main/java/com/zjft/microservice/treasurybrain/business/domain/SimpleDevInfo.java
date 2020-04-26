package com.zjft.microservice.treasurybrain.business.domain;

import java.io.Serializable;

public class SimpleDevInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private String no;
	private Integer devCatalog;
	private Integer cycleFlag;
	private Integer devStantardSize;
	private String cashboxLimit;

	public SimpleDevInfo() {
		super();
	}

	public SimpleDevInfo(String no) {
		super();
		this.no = no;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public Integer getDevCatalog() {
		return devCatalog;
	}

	public void setDevCatalog(Integer devCatalog) {
		this.devCatalog = devCatalog;
	}

	public Integer getCycleFlag() {
		return cycleFlag;
	}

	public void setCycleFlag(Integer cycleFlag) {
		this.cycleFlag = cycleFlag;
	}

	public Integer getDevStantardSize() {
		return devStantardSize;
	}

	public void setDevStantardSize(Integer devStantardSize) {
		this.devStantardSize = devStantardSize;
	}

	public String getCashboxLimit() {
		return cashboxLimit;
	}

	public void setCashboxLimit(String cashboxLimit) {
		this.cashboxLimit = cashboxLimit;
	}

}
