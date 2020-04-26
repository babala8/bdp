package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import com.zjft.microservice.treasurybrain.channelcenter.service.ClrCenterService;
import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-07
 */
@Slf4j
@RestController
public class ClrCenterInnerResourceImpl implements ClrCenterInnerResource{

	@Resource
	private ClrCenterService clrCenterService;

	@Override
	public List<ClrCenterTable> getClrCenterListByOrgNo(String orgNo) {
		return clrCenterService.getClrCenterListByOrgNo(orgNo);
	}

	@Override
	public List<Map<String, Object>> getClrCenterByClrNo(String clrCenterNo) {
		return clrCenterService.getClrCenterByClrNo(clrCenterNo);
	}

	@Override
	public ClrCenterTable selectByPrimaryKey(String clrCenterNo) {
		return clrCenterService.selectByPrimaryKey(clrCenterNo);
	}

	@Override
	public List<String> getClrCenterOrgNo(String clrCenterNo) {
		return clrCenterService.getClrCenterOrgNo(clrCenterNo);
	}

	@Override
	public List<String> clrCenterNoList() {
		return clrCenterService.clrCenterNoList();
	}

	@Override
	public String getOrgNameByClrNo(String centerNo) {
		return clrCenterService.getOrgNameByClrNo(centerNo);
	}

	@Override
	public void updateByPrimaryKeySelective(ClrCenterTable clrCenterTable) {
		clrCenterService.updateByPrimaryKeySelective(clrCenterTable);
	}

	@Override
	public Boolean qryClrCenterIsAuto(String clrCenterNo){
		return clrCenterService.qryClrCenterIsAuto(clrCenterNo);
	}

}
