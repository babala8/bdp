package com.zjft.microservice.treasurybrain.channelcenter.domain;

import com.zjft.microservice.treasurybrain.channelcenter.po.CallCustomerLineRunPO;
import lombok.Data;

import java.util.List;

/**
 * @author liuyuan
 * @since 2019/9/23 18:47
 */

@Data
public class CallCustomerLineRunDO extends CallCustomerLineRunPO {

	private String lineName;

	private List<CallCustomerLineRunDetailDO> detailList;

	public List<CallCustomerLineRunDetailDO> getDetailDOList(){
		return this.detailList;
	}

	public void setDetailList(List<CallCustomerLineRunDetailDO> detailList){
		this.detailList = detailList;
	}
}
