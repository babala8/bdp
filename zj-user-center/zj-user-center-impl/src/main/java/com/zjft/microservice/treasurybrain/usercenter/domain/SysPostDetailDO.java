package com.zjft.microservice.treasurybrain.usercenter.domain;

import com.zjft.microservice.treasurybrain.usercenter.po.SysPostLimitPO;
import com.zjft.microservice.treasurybrain.usercenter.po.SysPostPO;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/14
 */
@Data
public class SysPostDetailDO extends SysPostPO {

	private List<SysPostLimitPO> postLimitList;
}
