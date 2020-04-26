package com.zjft.microservice.treasurybrain.usercenter.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @since 2019/9/25 10:20
 */

public interface ScheduleService {


	PageDTO<OutSourcingLineMapDTO> qryOutSourcingByPage(Map<String, Object> paramsMap);

	DTO addOutSourcing(OutSourcingLineMapAddDTO outSourcingLineMapAddDTO);

	DTO deleteOutSourcing(String no);

	DTO modOutSourcing(OutSourcingLineMapDTO outSourcingLineMapDTO) ;

	DTO export(HttpServletRequest request, HttpServletResponse response, OutSourcingLineMapExportDTO outSourcingLineMapExportDTO);



	DTO addPostScheduleMould(PostScheduleMouldDTO postScheduleMouldDTO);

	PageDTO<PostScheduleMouldDTO> qryPostScheduleMouldBypage(Map<String, Object> param);

	DTO modPostScheduleMould(PostScheduleMouldDTO postScheduleMouldDTO);

	DTO deletePostScheduleMould(String mouldNo);

	ListDTO<PostScheduleMouldDTO> qryAllMouldByOrgNoAndPostNo(Map<String, Object> map);



	DTO createSchedule(CreateScheduleDTO createScheduleDTO);

	DTO modSchedule(PostScheduleDTO postScheduleDTO);

	PageDTO<PostScheduleDTO> qryScheduleByPage(Map<String, Object> param);

	DTO deleteSchedule(String planNo);

	/**
	 *
	 * 根据日期查询外包人员排班信息
	 * @param dutyDate 日期
	 * @return
	 */
	List<OutSourcingLineMapInnerDTO> qryInfoByDutyDate(String dutyDate);

	List<String> qryOpNameByLineRunNo(String lineRunNo);
}
