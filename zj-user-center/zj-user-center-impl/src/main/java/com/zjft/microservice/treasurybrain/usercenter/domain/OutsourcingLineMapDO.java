package com.zjft.microservice.treasurybrain.usercenter.domain;

import com.zjft.microservice.treasurybrain.usercenter.po.OutsourcingLineMapPO;
import com.zjft.microservice.treasurybrain.usercenter.po.OutsourcingPO;
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
