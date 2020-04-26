package com.zjft.microservice.treasurybrain.linecenter.domain;

import com.zjft.microservice.treasurybrain.common.domain.AddnoteLine;
import lombok.Data;

import java.util.List;

@Data
public class AddnotesPlanDO {
    private String addnotesPlanNo;

    private String clrCenterNo;

    private String planAddnotesDate;

    private String planStartTime;

    private String lastestEndTime;

    private Integer planDevCount;

    private Long planAddnotesAmt;

    private Integer planGenMode;

    private String planGenOpNo;

    private String planGenDate;

    private String planGenTime;

    private Integer status;

    private String submitOpNo;

    private String submitDate;

    private String submitTime;

    private String modOpNo;

    private String modDate;

    private String modTime;

    private Integer lineMode;

    private String lineNo;

    private String auditOpNo;

    private String auditDate;

    private String auditTime;

    private String refuseSuggestion;

    private String note;

	private Integer isUrgency;

    private String clrCenterName;//ClrCenterTable clrCenterTable;

	private  String   planGenOpName ;//SysUserDO planGenUser;

	private String auditOpName ;//SysUserDO auditOpUser;

    private List<AddnoteLine> addnoteLineList;


}
