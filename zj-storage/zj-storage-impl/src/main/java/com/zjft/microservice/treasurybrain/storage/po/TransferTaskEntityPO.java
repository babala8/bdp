package com.zjft.microservice.treasurybrain.storage.po;

import lombok.Data;

@Data
public class TransferTaskEntityPO {
	private String taskNo;

	private String containerNo;

    private String customerNo;

    private Integer entityType;

    private Integer direction;

    private String upperNo;

    private Integer leafFlag;

    private Integer status;

    private String note;

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getUpperNo() {
        return upperNo;
    }

    public void setUpperNo(String upperNo) {
        this.upperNo = upperNo == null ? null : upperNo.trim();
    }

    public Integer getLeafFlag() {
        return leafFlag;
    }

    public void setLeafFlag(Integer leafFlag) {
        this.leafFlag = leafFlag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}
