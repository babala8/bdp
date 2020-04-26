package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import com.zjft.microservice.treasurybrain.channelcenter.service.SysOrgService;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-02
 */
@Slf4j
@RestController
public class SysOrgInnerResourceImpl implements SysOrgInnerResource{

	@Resource
	private SysOrgService sysOrgService;


	@Override
	public List<String> qryOrgRegion(String clrOrgNo) {
		return sysOrgService.qryOrgRegion(clrOrgNo);
	}

	@Override
	public List<Map<String, Object>> getNetpointsByClrNo(String clrCenterNo) {
		return sysOrgService.getNetpointsByClrNo(clrCenterNo);
	}

	@Override
	public int qryOrgGradeNoByOrgNo(String orgNo){
		return sysOrgService.qryOrgGradeNoByOrgNo(orgNo);
	}

	@Override
	public int qryClrCenterFlag(String orgNo){
		return sysOrgService.qryClrCenterFlag(orgNo);
	}

	@Override
	public Map<String, String> qryCenterByNo(String no){
		return sysOrgService.qryCenterByNo(no);
	}

	@Override
	public Map<String, String> qryDevInfoByNo(String devNo){
		return sysOrgService.qryDevInfoByNo(devNo);
	}

	@Override
	public List<String> selectOrgNoList(String lineNo,Integer orgGradeNo){
		return sysOrgService.selectOrgNoList(lineNo,orgGradeNo);
	}

	@Override
	public List<SysOrgDTO> qryNetworksByNetworkLineNo(String clrCenterNo, String networkLineNo){
		return sysOrgService.qryNetworksByNetworkLineNo(clrCenterNo,networkLineNo);
	}

	@Override
	public String selectLineNo(String customerNo){
		return sysOrgService.selectLineNo(customerNo);
	}

}
