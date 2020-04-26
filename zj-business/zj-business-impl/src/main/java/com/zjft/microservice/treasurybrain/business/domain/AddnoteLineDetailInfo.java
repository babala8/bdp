package com.zjft.microservice.treasurybrain.business.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddnoteLineDetailInfo extends AddnoteLineDetailInfoKey {
    private BigDecimal taskCount;

    private String lineNo;

    private String taskOneType;

    private String taskTwoType;

    private String startTimeAm;

    private String endTimeAm;

    private String startTimePm;

    private String endTimePm;

    private String returnUnitAm;

    private String returnUnitPm;

    public BigDecimal getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(BigDecimal taskCount) {
        this.taskCount = taskCount;
    }

    public String getTaskOneType() {
        return taskOneType;
    }

    public void setTaskOneType(String taskOneType) {
        this.taskOneType = taskOneType == null ? null : taskOneType.trim();
    }

    public String getTaskTwoType() {
        return taskTwoType;
    }

    public void setTaskTwoType(String taskTwoType) {
        this.taskTwoType = taskTwoType == null ? null : taskTwoType.trim();
    }

    public String getStartTimeAm() {
        return startTimeAm;
    }

    public void setStartTimeAm(String startTimeAm) {
        this.startTimeAm = startTimeAm == null ? null : startTimeAm.trim();
    }

    public String getEndTimeAm() {
        return endTimeAm;
    }

    public void setEndTimeAm(String endTimeAm) {
        this.endTimeAm = endTimeAm == null ? null : endTimeAm.trim();
    }

    public String getStartTimePm() {
        return startTimePm;
    }

    public void setStartTimePm(String startTimePm) {
        this.startTimePm = startTimePm == null ? null : startTimePm.trim();
    }

    public String getEndTimePm() {
        return endTimePm;
    }

    public void setEndTimePm(String endTimePm) {
        this.endTimePm = endTimePm == null ? null : endTimePm.trim();
    }

    public String getReturnUnitAm() {
        return returnUnitAm;
    }

    public void setReturnUnitAm(String returnUnitAm) {
        this.returnUnitAm = returnUnitAm == null ? null : returnUnitAm.trim();
    }

    public String getReturnUnitPm() {
        return returnUnitPm;
    }

    public void setReturnUnitPm(String returnUnitPm) {
        this.returnUnitPm = returnUnitPm == null ? null : returnUnitPm.trim();
    }
}
