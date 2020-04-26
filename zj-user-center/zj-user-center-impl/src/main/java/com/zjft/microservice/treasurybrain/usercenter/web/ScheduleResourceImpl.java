package com.zjft.microservice.treasurybrain.usercenter.web;

import com.zjft.microservice.treasurybrain.usercenter.dto.CreateScheduleDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.dto.*;
import com.zjft.microservice.treasurybrain.usercenter.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/25 10:17
 */
@Slf4j
@RestController
public class ScheduleResourceImpl implements ScheduleResource {

	@Resource
	ScheduleService scheduleService;

	@Override
	public PageDTO<OutSourcingLineMapDTO> qryOutSourcingByPage(Map<String, Object> paramsMap) {
		try {

			return scheduleService.qryOutSourcingByPage(paramsMap);
		} catch (Exception e) {
			log.error("发生异常", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}

	}

	@Override
	public DTO addOutSourcing(OutSourcingLineMapAddDTO outSourcingLineMapAddDTO) {
		try {
			return scheduleService.addOutSourcing(outSourcingLineMapAddDTO);
		} catch (Exception e) {
			log.error("发生异常", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO deleteOutSourcing(String no) {

		try {
			return scheduleService.deleteOutSourcing(no);
		} catch (Exception e) {
			log.error("发生异常", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO modOutSourcing(OutSourcingLineMapDTO outSourcingLineMapDTO) {

		try {
			return scheduleService.modOutSourcing(outSourcingLineMapDTO);
		} catch (Exception e) {
			log.error("发生异常", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO export(HttpServletRequest request, HttpServletResponse response,
					  OutSourcingLineMapExportDTO outSourcingLineMapExportDTO) {
		try {
			return scheduleService.export(request, response, outSourcingLineMapExportDTO);
		} catch (Exception e) {
			log.error("发生异常", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}


	@Override
	public DTO addPostScheduleMould(PostScheduleMouldDTO postScheduleMouldDTO) {
		try {
			if (StringUtil.isNullorEmpty(postScheduleMouldDTO.getPostNo()) || StringUtil.isNullorEmpty(postScheduleMouldDTO.getMouldName()) ||
					postScheduleMouldDTO.getMouldType() == null || StringUtil.isNullorEmpty(postScheduleMouldDTO.getOrgNo())) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return scheduleService.addPostScheduleMould(postScheduleMouldDTO);
		} catch (Exception e) {
			log.error("新增模板失败！", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public PageDTO<PostScheduleMouldDTO> qryPostScheduleMouldBypage(Map<String, Object> param) {
		try {
			return scheduleService.qryPostScheduleMouldBypage(param);
		} catch (Exception e) {
			log.error("分页查询模板异常！");
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO modPostScheduleMould(PostScheduleMouldDTO postScheduleMouldDTO) {
		try {
			return scheduleService.modPostScheduleMould(postScheduleMouldDTO);
		} catch (Exception e) {
			log.error("修改模板异常！", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO deletePostScheduleMould(String mouldNo) {
		try {
			if (StringUtil.isNullorEmpty(mouldNo)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return scheduleService.deletePostScheduleMould(mouldNo);
		} catch (Exception e) {
			log.error("删除模板异常！", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ListDTO<PostScheduleMouldDTO> qryAllMouldByOrgNoAndPostNo(Map<String, Object> map) {
		try {
			return scheduleService.qryAllMouldByOrgNoAndPostNo(map);
		} catch (Exception e) {
			log.error("查询模板信息异常！");
			return new ListDTO<>(RetCodeEnum.EXCEPTION);
		}
	}


	@Override
	public DTO createSchedule(CreateScheduleDTO createScheduleDTO) {
		try {
			if (StringUtil.isNullorEmpty(createScheduleDTO.getMouldNo())) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return scheduleService.createSchedule(createScheduleDTO);
		} catch (Exception e) {
			log.error("生成排班信息失败！");
			return new DTO(RetCodeEnum.FAIL);
		}
	}

	@Override
	public DTO modSchedule(PostScheduleDTO postScheduleDTO) {
		try {
			return scheduleService.modSchedule(postScheduleDTO);
		} catch (Exception e) {
			log.error("修改排班信息失败");
			return new DTO(RetCodeEnum.FAIL);
		}
	}

	@Override
	public PageDTO<PostScheduleDTO> qryScheduleBypage(Map<String, Object> param) {
		try {
			return scheduleService.qryScheduleByPage(param);
		} catch (Exception e) {
			log.error("分页查询排班信息失败！");
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO deleteSchedule(String planNo) {
		try {
			if (StringUtil.isNullorEmpty(planNo)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return scheduleService.deleteSchedule(planNo);
		} catch (Exception e) {
			log.error("删除排班计划失败！");
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

}
