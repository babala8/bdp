package com.zjft.microservice.treasurybrain.channelcenter.domain;

import com.zjft.microservice.treasurybrain.channelcenter.po.OutsourcingLineMapPO;
import com.zjft.microservice.treasurybrain.channelcenter.po.OutsourcingPO;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/25 10:38
 */
@Data
public class OutsourcingLineMapDO extends OutsourcingLineMapPO {

	private String lineName;

	private String carNum;

	private List<OutsourcingPO> outsourcingList;
}
