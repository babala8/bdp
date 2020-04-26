package com.zjft.microservice.treasurybrain.usercenter.domain;

import com.zjft.microservice.treasurybrain.usercenter.po.PostScheduleMouldPO;
import lombok.Data;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/11/18
 */
@Data
public class PostScheduleMouldDO extends PostScheduleMouldPO {

	private String orgName;

	private String postName;

	List<PostScheduleMouldDetailDO> detailList;
}
