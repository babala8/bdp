package com.zjft.microservice.treasurybrain.business.domain;

public class DevCashClear extends DevCashClearKey {
    private String addcashDatetime;

    private Integer addcashAmount;

    private String addcashType;

    private String addcashCount;

    private String clearDatetime;

    private Integer addcashLeft;

    private Integer addcashLastamount;

    private Integer addcashRetractcount;

    private Integer depositCount;

    private Integer depositAmount;

    private Integer withdrawCount;

    private Integer withdrawAmount;

    private String clearId;

    private String cashutilAmount;

    private String cashbyHandcount;

    private String addId;

    private String stDate;

    public String getAddcashDatetime() {
        return addcashDatetime;
    }

    public void setAddcashDatetime(String addcashDatetime) {
        this.addcashDatetime = addcashDatetime == null ? null : addcashDatetime.trim();
    }

    public Integer getAddcashAmount() {
        return addcashAmount;
    }

    public void setAddcashAmount(Integer addcashAmount) {
        this.addcashAmount = addcashAmount;
    }

    public String getAddcashType() {
        return addcashType;
    }

    public void setAddcashType(String addcashType) {
        this.addcashType = addcashType == null ? null : addcashType.trim();
    }

    public String getAddcashCount() {
        return addcashCount;
    }

    public void setAddcashCount(String addcashCount) {
        this.addcashCount = addcashCount == null ? null : addcashCount.trim();
    }

    public String getClearDatetime() {
        return clearDatetime;
    }

    public void setClearDatetime(String clearDatetime) {
        this.clearDatetime = clearDatetime == null ? null : clearDatetime.trim();
    }

    public Integer getAddcashLeft() {
        return addcashLeft;
    }

    public void setAddcashLeft(Integer addcashLeft) {
        this.addcashLeft = addcashLeft;
    }

    public Integer getAddcashLastamount() {
        return addcashLastamount;
    }

    public void setAddcashLastamount(Integer addcashLastamount) {
        this.addcashLastamount = addcashLastamount;
    }

    public Integer getAddcashRetractcount() {
        return addcashRetractcount;
    }

    public void setAddcashRetractcount(Integer addcashRetractcount) {
        this.addcashRetractcount = addcashRetractcount;
    }

    public Integer getDepositCount() {
        return depositCount;
    }

    public void setDepositCount(Integer depositCount) {
        this.depositCount = depositCount;
    }

    public Integer getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Integer depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Integer getWithdrawCount() {
        return withdrawCount;
    }

    public void setWithdrawCount(Integer withdrawCount) {
        this.withdrawCount = withdrawCount;
    }

    public Integer getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Integer withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getClearId() {
        return clearId;
    }

    public void setClearId(String clearId) {
        this.clearId = clearId == null ? null : clearId.trim();
    }

    public String getCashutilAmount() {
        return cashutilAmount;
    }

    public void setCashutilAmount(String cashutilAmount) {
        this.cashutilAmount = cashutilAmount == null ? null : cashutilAmount.trim();
    }

    public String getCashbyHandcount() {
        return cashbyHandcount;
    }

    public void setCashbyHandcount(String cashbyHandcount) {
        this.cashbyHandcount = cashbyHandcount == null ? null : cashbyHandcount.trim();
    }

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId == null ? null : addId.trim();
    }

    public String getStDate() {
        return stDate;
    }

    public void setStDate(String stDate) {
        this.stDate = stDate == null ? null : stDate.trim();
    }
}
