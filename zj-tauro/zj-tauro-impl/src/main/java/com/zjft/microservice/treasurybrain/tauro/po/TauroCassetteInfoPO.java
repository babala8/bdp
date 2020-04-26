package com.zjft.microservice.treasurybrain.tauro.po;

import lombok.Data;

@Data
public class TauroCassetteInfoPO {
    private String cassetteNo;

    private String cassetteNoBank;

    private String cassetteSerial;

    private String tagTid;

    private Integer cassetteType;

    private Integer cassetteNoteValue;

    private String cassetteCurrency;

    private Integer cassetteMaxNotesnum;

    private Integer cassetteVendor;

    private Integer status;


}
