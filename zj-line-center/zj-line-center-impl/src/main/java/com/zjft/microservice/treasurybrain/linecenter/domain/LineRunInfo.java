package com.zjft.microservice.treasurybrain.linecenter.domain;

import lombok.Data;

import java.util.List;

@Data
public class LineRunInfo {
	private String lineRunNo;

	private String lineNo;

	private String lineName;

	private String theYearMonth;

	private String theDay;

	private Integer taskCount;

	private Integer devCount;

	private String startTimeAm;

	private String endTimeAm;

	private String startTimePm;

	private String endTimePm;

	private String returnUnitAm;

	private String returnUnitPm;

	private List<LineRunDevDetailExpandDO> devList;

	public void setLineRunNo(String lineRunNo) {
		this.lineRunNo = lineRunNo == null ? null : lineRunNo.trim();
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo == null ? null : lineNo.trim();
	}

	public void setLineName(String lineName) {
		this.lineName = lineName == null ? null : lineName.trim();
	}

	public void setTheYearMonth(String theYearMonth) {
		this.theYearMonth = theYearMonth == null ? null : theYearMonth.trim();
	}

	public void setTheDay(String theDay) {
		this.theDay = theDay == null ? null : theDay.trim();
	}

	public void setStartTimeAm(String startTimeAm) {
		this.startTimeAm = startTimeAm == null ? null : startTimeAm.trim();
	}

	public void setEndTimeAm(String endTimeAm) {
		this.endTimeAm = endTimeAm == null ? null : endTimeAm.trim();
	}

	public void setStartTimePm(String startTimePm) {
		this.startTimePm = startTimePm == null ? null : startTimePm.trim();
	}

	public void setEndTimePm(String endTimePm) {
		this.endTimePm = endTimePm == null ? null : endTimePm.trim();
	}

	public void setReturnUnitAm(String returnUnitAm) {
		this.returnUnitAm = returnUnitAm == null ? null : returnUnitAm.trim();
	}

	public void setReturnUnitPm(String returnUnitPm) {
		this.returnUnitPm = returnUnitPm == null ? null : returnUnitPm.trim();
	}
}
