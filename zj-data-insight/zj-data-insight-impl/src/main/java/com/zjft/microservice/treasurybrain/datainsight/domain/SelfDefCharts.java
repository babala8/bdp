package com.zjft.microservice.treasurybrain.datainsight.domain;

import lombok.Data;

@Data
public class SelfDefCharts {
    private String chartId;

    private String chartName;

    private Integer chartSubject;

    private String chartType;
    
    private String chartIcon;

	private String chartDesc;

    private Integer chartStatus;

    private String creator;

	private String createTime;

    private String lastestModOp;

    private String lastestModTime;

    private String chartDef;
    
    private String chartOption;
    
    private String creatorOrgno;
    
    private Integer count; //createorupdate使用，无业务含义
    
    private ChartSubject chartSubjectDetail;
    
    private ChartType chartTypeDetail;

}