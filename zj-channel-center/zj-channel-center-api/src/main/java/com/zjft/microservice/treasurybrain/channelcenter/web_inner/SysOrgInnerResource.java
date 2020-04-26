package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 */
public interface SysOrgInnerResource {

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<String> qryOrgRegion(@RequestParam("clrOrgNo") String clrOrgNo);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<Map<String, Object>> getNetpointsByClrNo(@RequestParam("clrCenterNo") String clrCenterNo);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	int qryOrgGradeNoByOrgNo(@RequestParam("orgNo") String orgNo);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	int qryClrCenterFlag(@RequestParam("orgNo") String orgNo);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	Map<String, String> qryCenterByNo(@RequestParam("no") String no);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	Map<String, String> qryDevInfoByNo(@RequestParam("devNo") String devNo);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<String> selectOrgNoList(@Param("lineNo")String lineNo,@Param("orgGradeNo")Integer orgGradeNo);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	List<SysOrgDTO> qryNetworksByNetworkLineNo(@Param("clrCenterNo") String clrCenterNo, @Param("networkLineNo") String networkLineNo);

	@ApiOperation(value = "（内部服务）", notes = "（内部服务）")
	String selectLineNo(@RequestParam("customerNo") String customerNo);


}
