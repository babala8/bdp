package com.zjft.microservice.treasurybrain.param.domain;

public class DevRuleExec extends DevRuleExecKey {

    private String addnotesRuleId;

    private Integer status;

    public String getAddnotesRuleId() {
        return addnotesRuleId;
    }

    public void setAddnotesRuleId(String addnotesRuleId) {
        this.addnotesRuleId = addnotesRuleId == null ? null : addnotesRuleId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
