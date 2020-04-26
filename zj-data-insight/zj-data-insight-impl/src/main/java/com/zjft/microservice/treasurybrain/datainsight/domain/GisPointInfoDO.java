package com.zjft.microservice.treasurybrain.datainsight.domain;

import lombok.Data;

/**
 * @author 杨光
 */
@Data
public class GisPointInfoDO {
    private String pointId;

    private String pointCreator;

    private String pointOrgno;

    private String createDate;

    private Integer status;

    private String lastestModOp;

    private String lastestModTime;

	private String pointHtml;

	private String pointJs;

	private Integer count; //updateOrcreate 无业务含义
}