package com.zjft.microservice.treasurybrain.channelcenter.domain;

import com.zjft.microservice.treasurybrain.channelcenter.po.CallCustomerLineRunDetailPO;
import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/23 18:52
 */
@Data
public class CallCustomerLineRunDetailDO extends CallCustomerLineRunDetailPO {

	private String customerShortName;
}
