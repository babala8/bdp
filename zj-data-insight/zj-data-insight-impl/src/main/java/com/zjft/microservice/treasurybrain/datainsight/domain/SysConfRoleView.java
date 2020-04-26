package com.zjft.microservice.treasurybrain.datainsight.domain;

public class SysConfRoleView {
    private Integer roleNo;

    private String roleDefView;

    public Integer getRoleNo() {
        return roleNo;
    }

    public void setRoleNo(Integer roleNo) {
        this.roleNo = roleNo;
    }

    public String getRoleDefView() {
        return roleDefView;
    }

    public void setRoleDefView(String roleDefView) {
        this.roleDefView = roleDefView == null ? null : roleDefView.trim();
    }
}