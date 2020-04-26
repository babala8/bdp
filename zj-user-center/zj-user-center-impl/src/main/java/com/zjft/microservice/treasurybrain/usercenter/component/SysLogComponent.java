package com.zjft.microservice.treasurybrain.usercenter.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.usercenter.dto.SysWebLogDTO;
import com.zjft.microservice.treasurybrain.usercenter.repository.SysLogMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author 常 健
 * @since 2020/1/8
 */
@Slf4j
@ZjComponentResource(group = "sysLog")
public class SysLogComponent {

	@Resource
	private SysLogMapper sysLogMapper;

	/**
	 * 用户日志查询
	 */
	@ZjComponentMapping("sysLogQry")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String sysLogQry(HashMap<String, Object> paramMap, PageDTO<SysWebLogDTO> returnDTO, String str) {
		returnDTO.setResult(RetCodeEnum.FAIL);
		int pageSize = PageUtil.transParam2Page(paramMap, returnDTO);
		Integer totalRow = sysLogMapper.qryTotalRow(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);
		List<SysWebLogDTO> sysWebLogDTOList = sysLogMapper.qrySysWebLogByPage(paramMap);
		returnDTO.setTotalRow(totalRow);
		returnDTO.setTotalPage(totalPage);
		returnDTO.setRetList(sysWebLogDTOList);
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		returnDTO.setRetCode("00");
		return "ok";
	}

	/**
	 * 查询失败
	 */
	@ZjComponentMapping("sysLogQryFail")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String sysLogQryFail(HashMap<String, Object> paramMap, PageDTO<SysWebLogDTO> returnDTO, String str) {
		returnDTO.setResult(RetCodeEnum.FAIL);
		returnDTO.setRetCode("FF");
		return "fail";
	}
}
