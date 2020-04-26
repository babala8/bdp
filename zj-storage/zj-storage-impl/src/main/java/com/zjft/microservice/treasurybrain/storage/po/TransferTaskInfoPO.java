package com.zjft.microservice.treasurybrain.storage.po;

import lombok.Data;

@Data
public class TransferTaskInfoPO {
    private String taskNo;

	private String transferType;

    private Integer taskType;

    private String planFinishDate;

    private Integer status;

    private String clrCenterNo;

    private String carNumber;

    private String lineNo;

    private String opNo1;

    private String opNo2;

    private String createOpNo;

    private String createTime;

    private String note;

    private String modeOp;

    private String modeTime;

    private String modeNote;

    private String auditOp;

    private String auditTime;

    private String auditNote;

    private Integer planDistance;

    private Integer planTimeCost;

    private Integer urgentFlag;

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo == null ? null : taskNo.trim();
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getPlanFinishDate() {
        return planFinishDate;
    }

    public void setPlanFinishDate(String planFinishDate) {
        this.planFinishDate = planFinishDate == null ? null : planFinishDate.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getClrCenterNo() {
        return clrCenterNo;
    }

    public void setClrCenterNo(String clrCenterNo) {
        this.clrCenterNo = clrCenterNo == null ? null : clrCenterNo.trim();
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber == null ? null : carNumber.trim();
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo == null ? null : lineNo.trim();
    }

    public String getOpNo1() {
        return opNo1;
    }

    public void setOpNo1(String opNo1) {
        this.opNo1 = opNo1 == null ? null : opNo1.trim();
    }

    public String getOpNo2() {
        return opNo2;
    }

    public void setOpNo2(String opNo2) {
        this.opNo2 = opNo2 == null ? null : opNo2.trim();
    }

    public String getCreateOpNo() {
        return createOpNo;
    }

    public void setCreateOpNo(String createOpNo) {
        this.createOpNo = createOpNo == null ? null : createOpNo.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getModeOp() {
        return modeOp;
    }

    public void setModeOp(String modeOp) {
        this.modeOp = modeOp == null ? null : modeOp.trim();
    }

    public String getModeTime() {
        return modeTime;
    }

    public void setModeTime(String modeTime) {
        this.modeTime = modeTime == null ? null : modeTime.trim();
    }

    public String getModeNote() {
        return modeNote;
    }

    public void setModeNote(String modeNote) {
        this.modeNote = modeNote == null ? null : modeNote.trim();
    }

    public String getAuditOp() {
        return auditOp;
    }

    public void setAuditOp(String auditOp) {
        this.auditOp = auditOp == null ? null : auditOp.trim();
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime == null ? null : auditTime.trim();
    }

    public String getAuditNote() {
        return auditNote;
    }

    public void setAuditNote(String auditNote) {
        this.auditNote = auditNote == null ? null : auditNote.trim();
    }

    public Integer getPlanDistance() {
        return planDistance;
    }

    public void setPlanDistance(Integer planDistance) {
        this.planDistance = planDistance;
    }

    public Integer getPlanTimeCost() {
        return planTimeCost;
    }

    public void setPlanTimeCost(Integer planTimeCost) {
        this.planTimeCost = planTimeCost;
    }

    public Integer getUrgentFlag() {
        return urgentFlag;
    }

    public void setUrgentFlag(Integer urgentFlag) {
        this.urgentFlag = urgentFlag;
    }
}
