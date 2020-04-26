package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.service.NetworkkLineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/24 19:28
 */
@Slf4j
@RestController
public class NetworkLineResourceImpl implements NetworkLineResource {

	@Resource
	private NetworkkLineService networkkLineService;

	@Override
	public ListDTO<LineTableDTO> qryAllLineByType(Map<String,Object> paramsMap) {

		try{
			return networkkLineService.getAllLineByType(paramsMap);
		}catch (Exception e){
			log.error("查询某类型设备",e);
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public List<String> qryTodayLine(String status) {
		return networkkLineService.getTodayLine(status);
	}

	/**
	 * 查询可执行线路
	 * @return 结果
	 */
	@Override
	public ListDTO qryLineNoList(Map<String, Object> paramMap) {
		ListDTO dto = new ListDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
		try {
			Map<String, Object> lineNoList = networkkLineService.qryLineNoList(paramMap);
			List retList = (List) lineNoList.get("lineNoList");
			dto.setRetList(retList);
			dto.setRetCode(StringUtil.parseString(lineNoList.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(lineNoList.get("retMsg")));
		} catch (Exception e) {
			log.error("查询线路失败", e);
			dto.setRetException("查询失败");
		}
		return dto;
	}
}
