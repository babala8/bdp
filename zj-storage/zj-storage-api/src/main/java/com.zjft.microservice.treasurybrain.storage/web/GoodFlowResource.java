package com.zjft.microservice.treasurybrain.storage.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageEntityCheckDTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageSortedTransferDTO;
import com.zjft.microservice.treasurybrain.storage.dto.StorageSortedTransferEntityDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/8/13 14:43
 */
@Api(tags = "仓储中心：实物出入库",value = "仓储中心：实物出入库")
public interface GoodFlowResource {

	String PREFIX = "${storage:}/v2/good";


	/**
	 * 现金库房调出
	 *
	 * 1.校验参数和笼车信息
	 * 2.插入库房调拨记录
	 * 3.增加库房物品信息
	 * @param
	 * @return
	 */
	@ApiOperation(value = "现金调出库房",notes = "现金调出库房")
	@PostMapping(path = PREFIX + "/inner/out")
	DTO StorageInnerOut(@RequestBody StorageSortedTransferDTO storageSortedTransferDTO);

	/**
	 * 现金库房调入
	 * 1.校验参数和笼车信息
	 * 2.插入库房调拨记录
	 * 3.删除库房物品信息
	 * @param
	 * @return
	 */
	@ApiOperation(value = "现金调入库房",notes = "现金调入库房")
	@PostMapping(path = PREFIX + "/inner/in")
	DTO StorageInnerIn(@RequestBody StorageSortedTransferDTO storageSortedTransferDTO);

	/**
	 *
	 * 分页查询现金库房调入调出记录
	 *
	 * @param params
	 * @return
	 */
	@ApiOperation(value = "分页查询调入调出记录",notes="分页查询调入调出记录")
	@GetMapping(path = PREFIX + "/inner/record")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "clrCenterNo",value = "所属金库编号", paramType = "query"),
			@ApiImplicitParam(name = "curPage", value = "当前页码", defaultValue = "1", required = true, paramType = "query"),
			@ApiImplicitParam(name = "pageSize", value = "每页条数", defaultValue = "10", required = true, paramType = "query")
	})
	PageDTO<StorageSortedTransferDTO> qryInnerTransferByPage(@RequestParam @ApiIgnore Map<String,Object> params);


	/**
	 *
	 * 查询调入调出详情
	 *
	 * @param recordNo
	 * @return
	 */
	@ApiOperation(value = "查询调入调出详情",notes = "查询调入调出详情")
	@GetMapping(path = PREFIX + "/inner/detail")
	ListDTO<StorageSortedTransferEntityDTO> qryInnerTransferDetail(@RequestParam String recordNo);

	/**
	 *
	 * 手持机扫描推送
	 *
	 * @param storageEntityCheckDTO
	 */
	@ApiOperation(value = "手持机扫描RFID",notes = "手持机扫描RFID")
	@PostMapping(path = PREFIX + "/pda")
	DTO accessCheck(@RequestBody StorageEntityCheckDTO storageEntityCheckDTO);

}
