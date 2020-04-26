package com.zjft.microservice.treasurybrain.linecenter.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.domain.LineTableDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.LineConverter;
import com.zjft.microservice.treasurybrain.linecenter.repository.LineTableMapper;
import com.zjft.microservice.treasurybrain.linecenter.service.NetworkkLineService;
import com.zjft.microservice.treasurybrain.task.web_inner.TaskInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/24 19:34
 */
@Slf4j
@Service
public class NetworkkLineServiceImpl implements NetworkkLineService {

	@Resource
	private LineTableMapper lineTableMapper;

	@Resource
	private TaskInnerResource taskInnerResource;

	@Override
	public ListDTO<LineTableDTO> getAllLineByType(Map<String,Object> paramsMap) {
//		String orgNo = sysUserResource.getAuthUserInfo().getOrgNo();
//		paramsMap.put("orgNo",orgNo);
		List<LineTableDO> lineDOS = lineTableMapper.getLineByLineType(paramsMap);
		List<LineTableDTO> lineTableDTOS = LineConverter.INSTANCE.do2dto(lineDOS);
		ListDTO<LineTableDTO> lineDOListDTO = new ListDTO<>(RetCodeEnum.SUCCEED);
		lineDOListDTO.addAll(lineTableDTOS);
		return lineDOListDTO;
	}

	@Override
	public List<String> getTodayLine(String status) {
		String opNo = ServletRequestUtil.getUsername();
		String planFinishDate = CalendarUtil.getSysTimeHMS();
		return lineTableMapper.getTodayLine(status,planFinishDate,opNo);
	}

	/**
	 * 查询可执行线路
	 *
	 * @return 结果
	 */
	@Override
	public Map<String, Object> qryLineNoList(Map<String, Object> paramMap) {
		List<Map<String, Object>> lineNoList = new ArrayList<>();
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			int taskType = StringUtil.objectToInt(paramMap.get("taskType"));
			String productNo = StringUtil.parseString(paramMap.get("productNo"));
			String planFinishDate = StringUtil.parseString(paramMap.get("planFinishDate"));
			String operateType = StringUtil.parseString(paramMap.get("operateType"));
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			if (taskType != -1) {
				paramsMap.put("taskType", taskType);
			}
			if (!"".equals(planFinishDate)) {
				paramsMap.put("planFinishDate", planFinishDate);
			}
			if (productNo != "" && productNo != null) {
				paramsMap.put("productNo", productNo);
			}
			if (operateType != "" && operateType != null) {
				paramsMap.put("operateType", operateType);
			}

			lineNoList = taskInnerResource.qryLineNoList(paramsMap);
			retMap.put("lineNoList", lineNoList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "查询成功！");
		} catch (Exception e) {
			log.error("查询线路失败", e);
			retMap.put("retCode", RetCodeEnum.FAIL.getCode());
			retMap.put("retMsg", "查询失败！");
		}
		return retMap;
	}
}
