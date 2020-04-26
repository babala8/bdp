package com.zjft.microservice.treasurybrain.business.dto;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import lombok.Data;

import java.util.List;

@Data
public class AddnotesPlanDTO extends DTO {

	private static final long serialVersionUID = 1L;

	private String addnotesPlanNo;

	private String clrCenterNo;

	private String clrCenterName;
	
	private String planAddnotesDate;

	private String planStartTime;

	private String lastestEndTime;

	private Integer planDevCount;

	private Double planAddnotesAmt;

	private Integer planGenMode;

	private String planGenOpNo;

	private String planGenOpName;

	private String planGenDate;

	private String planGenTime;

	private int status;

	private String submitOpNo;

	private String submitDate;

	private String submitTime;

	private String lineMode;

	private String lineNo;

	private List<AddnotesLineDTO> lineList;

	private String modOpNo;

	private String modDate;

	private String modTime;
	
	private String note;

	private String auditOpNo;

	private String auditOpName;

	private String auditDate;

	private String auditTime;

	private String refuseSuggestion;

	private Integer isUrgency;


	public String getClrCenterName() {
		return clrCenterName;
	}
	
	public void setClrCenterName(String clrCenterName) {
		this.clrCenterName = clrCenterName;
	}
	
	public String getAddnotesPlanNo() {
		return addnotesPlanNo;
	}

	public void setAddnotesPlanNo(String addnotesPlanNo) {
		this.addnotesPlanNo = addnotesPlanNo == null ? null : addnotesPlanNo
				.trim();
	}

	public String getClrCenterNo() {
		return clrCenterNo;
	}

	public void setClrCenterNo(String clrCenterNo) {
		this.clrCenterNo = clrCenterNo == null ? null : clrCenterNo.trim();
	}

	public String getPlanAddnotesDate() {
		return planAddnotesDate;
	}

	public void setPlanAddnotesDate(String planAddnotesDate) {
		this.planAddnotesDate = planAddnotesDate == null ? null
				: planAddnotesDate.trim();
	}

	public String getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(String planStartTime) {
		this.planStartTime = planStartTime == null ? null : planStartTime
				.trim();
	}

	public String getLastestEndTime() {
		return lastestEndTime;
	}

	public void setLastestEndTime(String lastestEndTime) {
		this.lastestEndTime = lastestEndTime == null ? null : lastestEndTime
				.trim();
	}

	public Integer getPlanDevCount() {
		return planDevCount;
	}

	public void setPlanDevCount(Integer planDevCount) {
		this.planDevCount = planDevCount;
	}

	public Number getPlanAddnotesAmt() {
		return planAddnotesAmt;
	}

	public void setPlanAddnotesAmt(Double planAddnotesAmt) {
		this.planAddnotesAmt = planAddnotesAmt;
	}

	public Integer getPlanGenMode() {
		return planGenMode;
	}

	public void setPlanGenMode(Integer planGenMode) {
		this.planGenMode = planGenMode;
	}

	public String getPlanGenOpNo() {
		return planGenOpNo;
	}

	public void setPlanGenOpNo(String planGenOpNo) {
		this.planGenOpNo = planGenOpNo == null ? null : planGenOpNo.trim();
	}

	public String getPlanGenDate() {
		return planGenDate;
	}

	public void setPlanGenDate(String planGenDate) {
		this.planGenDate = planGenDate == null ? null : planGenDate.trim();
	}

	public String getPlanGenTime() {
		return planGenTime;
	}

	public void setPlanGenTime(String planGenTime) {
		this.planGenTime = planGenTime == null ? null : planGenTime.trim();
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSubmitOpNo() {
		return submitOpNo;
	}

	public void setSubmitOpNo(String submitOpNo) {
		this.submitOpNo = submitOpNo == null ? null : submitOpNo.trim();
	}

	public String getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(String submitDate) {
		this.submitDate = submitDate == null ? null : submitDate.trim();
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime == null ? null : submitTime.trim();
	}

	public String getLineMode() {
		return lineMode;
	}

	public void setLineMode(String lineMode) {
		this.lineMode = lineMode;
	}

	public String getLineNo() {
		return lineNo;
	}

	public void setLineNo(String lineNo) {
		this.lineNo = lineNo == null ? null : lineNo.trim();
	}

	public String getModOpNo() {
		return modOpNo;
	}

	public void setModOpNo(String modOpNo) {
		this.modOpNo = modOpNo == null ? null : modOpNo.trim();
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate == null ? null : modDate.trim();
	}

	public String getModTime() {
		return modTime;
	}

	public void setModTime(String modTime) {
		this.modTime = modTime == null ? null : modTime.trim();
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note == null ? null : note.trim();
	}

}
