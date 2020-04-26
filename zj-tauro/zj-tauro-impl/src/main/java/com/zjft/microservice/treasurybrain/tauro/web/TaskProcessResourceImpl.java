package com.zjft.microservice.treasurybrain.tauro.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.tauro.dto.TagReaderCoordsDTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TaskProcessDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TaskProcessDetailDTO;
import com.zjft.microservice.treasurybrain.tauro.service.TaskProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/8/9
 */
@Slf4j
@RestController
public class TaskProcessResourceImpl implements TaskProcessResource {

	@Resource
	private TaskProcessService taskProcessService;

	@Override
	public PageDTO<TaskProcessDTO> queryDispatchByPage(Map<String, Object> paramMap) {
		log.info("------------[queryDispatchByPage]TaskProcessResource---------------");
		try {
			return taskProcessService.queryDispatchByPage(paramMap);
		}catch (Exception e){
			log.error("查询流程任务信息失败",e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public TaskProcessDetailDTO queryDispatchDetail(String taskNo) {
		log.info("------------[queryDispatchDetail]TaskProcessResource---------------");
		try {
			if (StringUtil.isNullorEmpty(taskNo)) {
				return new TaskProcessDetailDTO();
			}
			return taskProcessService.queryDispatchDetail(taskNo);
		}catch (Exception e){
			log.error("查询流程任务信息详情失败",e);
			return new TaskProcessDetailDTO();
		}
	}

	@Override
	public DTO addTagReaderCoordsInfo(TagReaderCoordsDTO tagReaderCoordsDTO) {
		log.info("------------[addHandReaderCoordsInfo]TaskProcessResource---------------");
		try {
			if (StringUtil.isNullorEmpty(tagReaderCoordsDTO.getTagReaderNo()) ||
					null == tagReaderCoordsDTO.getX() || null == tagReaderCoordsDTO.getY()) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return taskProcessService.addTagReaderCoordsInfo(tagReaderCoordsDTO);
		}catch (Exception e){
			log.error("新增手持机坐标信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ListDTO<TagReaderCoordsDTO> queryCoordsByTaskNo(String taskNo) {
		log.info("------------[queryCoordsByTaskNo]TaskProcessResource---------------");
		try {
			return taskProcessService.queryCoordsByTaskNo(taskNo);
		}catch (Exception e){
			log.error("查询手持机坐标信息失败", e);
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}
}
