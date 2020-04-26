package com.zjft.microservice.treasurybrain.accountscenter.po;

import lombok.Data;

@Data
public class BiztxlogPO {

	private String tranDate;

	private String txSerialNo;

	private String termId;

	private String area;

	private String channelId;

	private String businessId;

	private String orgNo;

	private String msgType;

	private String msgCodeType;

	private String msgCode;

	private String primaryAccount;

	private String curCode;

	private Long amount;

	private String settleDate;

	private String tranTime;

	private String debitAccountNo;

	private String debitAccountType;

	private String creditAccountNo;

	private String creditAccountType;

	private Integer cardMedium;

	private Integer agentType;

	private Integer cardFlag;

	private Integer txType;

	private String entryMode;

	private String origTxSerialNo;

	private String netFlag;

	private String respCode;

	private String txStatus;

	private String termSerialNo;

	private Integer termReconciliation;

	private String termBatchNo;

	private String termDate;

	private String termTime;

	private String hostSerialNo;

	private String hostRespCode;

	private String hostSettleDate;

	private Integer hostReconciliation;

	private String hostTime;

	private String hostDate;

	private String compTraceNo;

	private String compRespCode;

	private String compSettleDate;

	private Integer compReconciliation;

	private String compTime;

	private String compDate;

	private Integer cupsBatchNo;

	private String transDateTime;

	private String cardSeq;

	private String icData;

	private String blackCardFlag;

	private String susCardFlag;

	private String dayTradeFlag;

	private String atmpSerialNo;

	private Integer cardMediumFlag;

	private String ucpId;

	private String note1;

	private String note2;

	private String note3;

	private String note4;

	private String note5;

	private String sysSeqNo;
}
