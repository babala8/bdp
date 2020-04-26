package com.zjft.microservice.treasurybrain.timejob.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.timejob.dto.JobLogDTO;
import com.zjft.microservice.treasurybrain.timejob.mapstruct.JobLogConverter;
import com.zjft.microservice.treasurybrain.timejob.po.JobLogPO;
import com.zjft.microservice.treasurybrain.timejob.repository.JobLogMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author 常 健
 * @since 2020/1/8
 */
@Slf4j
@ZjComponentResource(group = "jobLog")
public class JobLogComponent {


	@Resource
	private JobLogMapper jobLogMapper;


	/**
	 * 定时任务日志分页查询
	 */
	@ZjComponentMapping("jobLogQry")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String jobLogQry(HashMap<String,Object> requestDTO, PageDTO<JobLogDTO> returnDTO, String str){
		returnDTO.setResult(RetCodeEnum.FAIL);
		Integer curPage = StringUtil.objectToInt(requestDTO.get("curPage"));
		Integer pageSize = StringUtil.objectToInt(requestDTO.get("pageSize"));
		requestDTO.put("startRow", pageSize * (curPage - 1));
		requestDTO.put("endRow", pageSize * curPage);

		int totalRow = jobLogMapper.qryTotalRow(requestDTO);
		int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

		List<JobLogPO> dolist = jobLogMapper.qryTimeJobLogByPage(requestDTO);
		List<JobLogDTO> dtoList = JobLogConverter.INSTANCE.domain2dto(dolist);
		returnDTO.setTotalRow(totalRow);
		returnDTO.setTotalPage(totalPage);
		returnDTO.setCurPage(curPage);
		returnDTO.setPageSize(pageSize);
		returnDTO.setRetList(dtoList);
		returnDTO.setResult(RetCodeEnum.SUCCEED);
		returnDTO.setRetCode("00");
		return "ok";
	}

	/**
	 * 查询失败
	 */
	@ZjComponentMapping("jobLogQryFail")
	@ZjComponentExitPaths({
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String jobLogQryFail(HashMap<String, Object> paramMap, PageDTO<JobLogDTO> returnDTO, String str) {
		returnDTO.setResult(RetCodeEnum.FAIL);
		returnDTO.setRetCode("FF");
		return "fail";
	}
}
