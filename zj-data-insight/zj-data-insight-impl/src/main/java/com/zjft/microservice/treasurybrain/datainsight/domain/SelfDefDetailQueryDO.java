package com.zjft.microservice.treasurybrain.datainsight.domain;

import lombok.Data;

@Data
public class SelfDefDetailQueryDO {
    private String id;

    private String name;

    private Integer status;

    private String creator;

    private String creatorOrgno;

    private String createTime;

    private String lastestModOp;

    private String lastestModTime;

    private String def;

    private Integer groupid;
    
    private SelfDefGroup selfDefGroup;
    
    private Integer count; //createorupdate使用，无业务含义

}