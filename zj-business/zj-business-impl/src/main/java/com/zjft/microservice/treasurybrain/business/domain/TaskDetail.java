package com.zjft.microservice.treasurybrain.business.domain;

import lombok.Data;

@Data
public class TaskDetail extends TaskDetailKey {
    private Integer customerType;

    private Integer sort;

    private String address;

    private Integer status;

    private String note;

	private String  orgNo;

	private  String orgName;

	private  String devTypeName;

	private  String devCatalogName;

	private  String devVendorName;

	private  String lineName;

	private  String lineNo;

	private  Integer amount;

	private  Integer addnotesAmount;

    public Integer getCustomerType() {
        return customerType;
    }

    public void setCustomerType(Integer customerType) {
        this.customerType = customerType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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
