package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

import java.util.List;

@Data
public class NetworkLineRunInfo {
	private String networkLineRunNo;

	private String networkLineNo;

	private String networkLineName;

	private String theYearMonth;

	private String theDay;

	private Integer networkCount;

	private List<NetworkLineRunOrgDetail> networkList;

	public String getNetworkLineRunNo() {
		return networkLineRunNo;
	}

	public void setNetworkLineRunNo(String networkLineRunNo) {
		this.networkLineRunNo = networkLineRunNo;
	}

	public String getNetworkLineNo() {
		return networkLineNo;
	}

	public void setNetworkLineNo(String networkLineNo) {
		this.networkLineNo = networkLineNo;
	}

	public String getNetworkLineName() {
		return networkLineName;
	}

	public void setNetworkLineName(String networkLineName) {
		this.networkLineName = networkLineName;
	}

	public String getTheYearMonth() {
		return theYearMonth;
	}

	public void setTheYearMonth(String theYearMonth) {
		this.theYearMonth = theYearMonth;
	}

	public String getTheDay() {
		return theDay;
	}

	public void setTheDay(String theDay) {
		this.theDay = theDay;
	}

	public Integer getNetworkCount() {
		return networkCount;
	}

	public void setNetworkCount(Integer networkCount) {
		this.networkCount = networkCount;
	}

	public List<NetworkLineRunOrgDetail> getNetworkList() {
		return networkList;
	}

	public void setNetworkList(List<NetworkLineRunOrgDetail> networkList) {
		this.networkList = networkList;
	}
}
