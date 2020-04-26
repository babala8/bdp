package com.zjft.microservice.treasurybrain.business.web;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * @author 杨志勇
 * @since 2019/10/11
 */
@Api(tags = "业务中心：个性业务管理",value = "业务中心：个性业务管理")
public interface SelfProductResource {
	String PREFIX = "${business:}/v2/selfProduct";
//
//	@PostMapping(PREFIX)
//	@ApiOperation(value = "产品业务申请", notes = "产品业务申请")
//	DTO applyForSelfProduct(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);


}
