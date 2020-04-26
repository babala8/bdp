package com.zjft.microservice.treasurybrain.common.domain;

import lombok.Data;

@Data
public class SysOrg {

    private String no;

    private String name;

    private String parentOrgNo;

    private SysOrg parentOrg;

    private Integer left;

    private Integer right;

    private Integer orgGradeNo;

    private SysOrgGrade orgGrade;

    private String moneyorgFlag;

    private String x;

    private String y;

    private String address;

    private String linkman;

    private String telephone;

    private String mobile;

    private String fax;

    private String email;

    private String businessRange;

    private String cupAreaCode;

    private String addressCode;

    private String areaNo;

    private String areaType;

    private String orgPhysicsCatalog;

    private String note;

    private String clrCenterNo;

    private ClrCenterTable clrCenter;

    private String city;

    private String region;

    private Integer status;

    private Integer clrCenterFlag;

    private String orgNo;

    private String shortName;

    private String fullName;

    private Integer awayFlag;

    private String clrCenterNoCash;

    private String networkLineNo;

    private String deliveryTime;

    private String backTime;

}
