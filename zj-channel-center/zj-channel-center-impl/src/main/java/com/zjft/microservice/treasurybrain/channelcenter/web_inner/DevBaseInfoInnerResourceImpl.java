package com.zjft.microservice.treasurybrain.channelcenter.web_inner;

import com.zjft.microservice.treasurybrain.channelcenter.service.DevBaseInfoService;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
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
public class DevBaseInfoInnerResourceImpl implements DevBaseInfoInnerResource{

	@Resource
	private DevBaseInfoService devBaseInfoService;

	@Override
	public DevBaseInfo selectByPrimaryKey(String no) {
		return devBaseInfoService.selectByPrimaryKey(no);
	}

	@Override
	public DevBaseInfo qryDevByNoOrgNo(String devNo, String orgNo) {
		return devBaseInfoService.qryDevByNoOrgNo(devNo, orgNo);
	}

	@Override
	public DevBaseInfo selectDetailByPrimaryKey(String devNo) {
		return devBaseInfoService.selectDetailByPrimaryKey(devNo);
	}

	@Override
	public void updateByPrimaryKeySelective(DevBaseInfo devBaseInfo) {
		devBaseInfoService.updateByPrimaryKeySelective(devBaseInfo);
	}

	@Override
	public List<Map<String, Object>> getKeyEventDevice(Map<String, Object> params) {
		return devBaseInfoService.getKeyEventDevice(params);
	}

	@Override
	public List<Map<String, Object>> getKeyEventDeviceForfault(Map<String, Object> params) {
		return devBaseInfoService.getKeyEventDeviceForfault(params);
	}

	@Override
	public List<Map<String, Object>> getKeyEventDeviceForLineRun(Map<String, Object> params) {
		return devBaseInfoService.getKeyEventDeviceForLineRun(params);
	}

	@Override
	public List<Map<String, Object>> getAvaileAmtAndTimeInterval(Map<String, Object> params) {
		return devBaseInfoService.getAvaileAmtAndTimeInterval(params);
	}

}
