package com.zjft.microservice.treasurybrain.tolly.web;

import com.zjft.microservice.handler.annotation.ZjMessageMapping;
import com.zjft.microservice.treasurybrain.business.dto.TransferTaskInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName TollySystemResource
 * @Description 第三方系统接入接出
 * @Author zhangjs
 * @CreateDate 2019/12/10 10:39
 * @Version 1.0.1
 * @modify zhangsan 2019-12-10 10:44:00 数据修正
 **/
public interface TollySystemResource {

	/**
	*@MethodName: applyForReceipt
	*@Description: 柜面-->运营：网点领现单申请
	*@Author: zhangjs
	*@Param: [transferTaskInfoDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/10 14:26
	**/
	@ZjMessageMapping("receiptCounter")
	DTO applyForReceipt(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);

	/**
	*@MethodName: applyForTransfer
	*@Description: 柜面-->运营：网点解现单申请
	*@Author: zhangjs
	*@Param: [transferTaskInfoDTO]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/10 14:27
	**/
	@ZjMessageMapping("transferCounter")
	DTO applyForTransfer(@RequestBody TransferTaskInfoDTO transferTaskInfoDTO);


	/**
	*@MethodName: logisticsInput
	*@Description: 物流-->运营：物流系统与运营系统的交互
	*@Author: zhangjs
	*@Param: [returnCode]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
    *                         returnCode：01-网点领现实物准备出库。  02-网点领现实物出库完成。  03-网点经办人员已接到押运人员押送的实物。
	*                                    04-物流已到达解现网点并已接受到实物，请开始押运。      05-押运人员已押送实解现物到达金库。
	*@Date: 2019/12/10 14:28
	**/
	@ZjMessageMapping("logisticsInput")
	DTO logisticsInput(@RequestParam("returnCode") String returnCode);

	/**
	*@MethodName: logisticsOutput
	*@Description: 运营-->物流：物流系统与运营系统的交互
	*@Author: zhangjs
	*@Param: [returnCode]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
    *                            returnCode：06-解现单审核通过，请物流系统进行调运。  07-解现实物已通过门禁并入库。
	*@Date: 2019/12/10 14:31
	**/
	@ZjMessageMapping("logisticsOutput")
	DTO logisticsOutput(@RequestParam("returnCode") String returnCode);

	/**
	*@MethodName: storageOutput
	*@Description: 运营-->仓储：运营系统与仓储系统的交互
	*@Author: zhangjs
	*@Param: [returnCode]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
    *                  returnCode：08- 仓储人员已确定领现箱出库操作，请开始出库。       09- 钞处领现已入库，请仓储系统入库。
    * 	              			  10- 仓储领现出库已到押运人员，请仓储开始进行出库。   11- 解现箱已入库，请仓储系统入库。
    * 	                         12- 钞处已申请仓储解现箱出库，请仓储系统开始出库。   13- 钞处解现已入库，请仓储系统入库。
	*@Date: 2019/12/10 14:32
	**/
	@ZjMessageMapping("storageOutput")
	DTO storageOutput(@RequestParam("returnCode") String returnCode);

	/**
	*@MethodName: storageInput
	*@Description: 仓储-->运营： 运营系统与仓储系统的交互
	*@Author: zhangjs
	*@Param: [returnCode]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
    *                        returnCode：14- 仓储系统工作已完成，请运营系统记录实物货位。（领现箱）   15- 仓储系统工作已完成，请运营系统记录实物货位。（解现箱）
    *                                   16- 仓储系统工作已完成，请运营系统记录实物货位。（钞处入库到仓储入货位—立体库）
	*@Date: 2019/12/10 14:34
	**/
	@ZjMessageMapping("storageInput")
	DTO storageInput(@RequestParam("storageInput") String returnCode);

	/**
	*@MethodName: clearCenterInput
	*@Description: 
	*@Author: zhangjs
	*@Param: [returnCode]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/10 14:35
	**/
	@ZjMessageMapping("clearCenterInput")
	DTO clearCenterInput(@RequestParam("clearCenterInput") String returnCode);

	/**
	*@MethodName: clearCenterOutput
	*@Description: 
	*@Author: zhangjs
	*@Param: [returnCode]
	*@Return: com.zjft.microservice.treasurybrain.common.dto.DTO
	*@Date: 2019/12/10 14:35
	**/
	@ZjMessageMapping("clearCenterOutput")
	DTO clearCenterOutput(@RequestParam("clearCenterOutput") String returnCode);

}
