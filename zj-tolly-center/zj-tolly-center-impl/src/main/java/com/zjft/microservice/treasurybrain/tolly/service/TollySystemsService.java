package com.zjft.microservice.treasurybrain.tolly.service;

import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;

/**
 * @ClassName TollySystemsService
 * @Description 第三方系统接入接出业务层
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
public interface TollySystemsService {

	/**
	*@MethodName: applyForReceipt
	*@Description: 网点调拨申请
	*@Author: zhangjs
	*@Param: TransferTaskInfoDTO
	*@Return: DTO
	*@Date: 2019/12/11 17:24
	**/
	DTO applyForReceipt(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	*@MethodName: applyForTransfer
	*@Description:
	*@Author: zhangjs
	*@Param:  TransferTaskInfoDTO
	*@Return:  DTO
	*@Date: 2019/12/11 17:24
	**/
	DTO applyForTransfer(TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	*@MethodName: logisticsInput
	*@Description: 
	*@Author: zhangjs
	*@Param:  returnCode
	*@Return:  DTO
	*@Date: 2019/12/11 17:25
	**/
	DTO logisticsInput(String returnCode);

}
