package com.zjft.microservice.treasurybrain.linecenter.domain;

import com.zjft.microservice.treasurybrain.common.domain.AddnoteLine;
import com.zjft.microservice.treasurybrain.common.domain.AddnotesPlanDetailKey;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class AddnotesPlanDetail extends AddnotesPlanDetailKey {
    private String keyEvent;

    private BigDecimal chsEstScore;

    private BigDecimal chsAuxScore;

    private Integer sortNo;

    private Integer cashReqAmt;

    private Integer planPredictAmt;

    private Integer planAddnotesAmt;

    private String keyEventDetail;

    private String lineNo;

    private Integer status;

    private String note;

    private String devCatalogName;

    private String address;

    private String devLineName;

    private String orgName;

    private Integer availableAmt;

    private String lastAddnoteDate;

    private String devStatusDevNo;

	private String sysOrgNo;

    private Integer devCatalogTableNo;

    private Integer useDays;

    private Integer devStantardSize;

    private Integer addClrPeriod;

	private Integer cycleFlag;

	private String taskNo;

//    private DevBaseInfo devBaseInfo;
    
    private AddnoteLine addnoteLine;
    
//    private DevStatusTable devStatusTable;

//	private DispatchDetailTable dispatchDetailTable;

    public void setKeyEvent(String keyEvent) {
        this.keyEvent = keyEvent == null ? null : keyEvent.trim();
    }

    public void setKeyEventDetail(String keyEventDetail) {
        this.keyEventDetail = keyEventDetail == null ? null : keyEventDetail.trim();
    }

    public String getLineNo() {
        return lineNo;
    }

    public void setLineNo(String lineNo) {
        this.lineNo = lineNo == null ? null : lineNo.trim();
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
