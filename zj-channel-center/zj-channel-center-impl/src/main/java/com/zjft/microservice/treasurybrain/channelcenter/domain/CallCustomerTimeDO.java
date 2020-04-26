package com.zjft.microservice.treasurybrain.channelcenter.domain;

import com.zjft.microservice.treasurybrain.channelcenter.po.CallCustomerTimePO;
import lombok.Data;

/**
 * @author liuyuan
 * @since 2019/9/17 11:25
 */
@Data
public class CallCustomerTimeDO extends CallCustomerTimePO {

	private String callCustomerLineName;
}
