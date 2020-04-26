package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-07
 */
public interface ClrCenterInnerResource {

	//@GetMapping(PREFIX + "/inner/clrCenters")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<ClrCenterTable> getClrCenterListByOrgNo(@RequestParam("orgNo") String orgNo);


	//@GetMapping(PREFIX + "/inner/clrCenter")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<Map<String, Object>> getClrCenterByClrNo(@RequestParam("clrCenterNo") String clrCenterNo);

	//@GetMapping(PREFIX + "/inner/clrCenterByPrimaryKey")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	ClrCenterTable selectByPrimaryKey(@RequestParam("clrCenterNo") String clrCenterNo);

	//@GetMapping(PREFIX + "/inner/clrCenterOrgNo")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<String> getClrCenterOrgNo(@RequestParam("clrCenterNo") String clrCenterNo);

	//@GetMapping(PREFIX + "/inner/clrCenterNoList")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<String> clrCenterNoList();

	//@GetMapping(PREFIX + "/inner/clrCenterOrgNameByClrNo")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	String getOrgNameByClrNo(@RequestParam("clrCenterNo") String clrCenterNo);

	//@PutMapping(PREFIX + "/inner/clrCenter")
	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	void updateByPrimaryKeySelective(@RequestBody ClrCenterTable clrCenterTable);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	Boolean qryClrCenterIsAuto(@RequestParam("clrCenterNo") String clrCenterNo);

}
