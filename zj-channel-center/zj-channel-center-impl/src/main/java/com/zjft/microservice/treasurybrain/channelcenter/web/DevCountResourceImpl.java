package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.CountTaskInfoDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevCountDTO;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevCountService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 崔耀中
 * @since 2019-10-10
 */

@Slf4j
@RestController
public class DevCountResourceImpl implements DevCountResource{

	@Resource
	private DevCountService devCountService;

	@Override
	public PageDTO<DevCountDTO> queryDevCountList(Map<String, Object> paramMap) {
		log.info("------------[queryDevCountList]DevCountInnerResource-------------");
		try {
			return devCountService.queryDevCountList(paramMap);
		} catch (Exception e) {
			log.error("查询设备列表失败", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO addDevCountInfo(DevCountDTO devCountDTO){
		log.info("------------[addDevCountInfo]DevCountInnerResource-------------");
		try {
			String devNo = devCountDTO.getDevNo();
			if (StringUtil.isNullorEmpty(devNo)){
				return new DTO(RetCodeEnum.FAIL);
			}else if (devCountService.queryByDevNo(devNo)>0){
				log.error("添加失败，设备编号已存在");
				return new DTO(RetCodeEnum.FAIL.getCode(),"添加失败，设备编号已存在");
			}
			return devCountService.addDevCountInfo(devCountDTO);
		}catch (Exception e){
			log.error("新增设备信息异常：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO modDevCountInfo(DevCountDTO devCountDTO){
		log.info("------------[modCarInfo]DevCountInnerResource-------------");
		try {
			String devNo = devCountDTO.getDevNo();
			if (devNo==null){
				return new DTO(RetCodeEnum.RESULT_EMPTY);
			}
			return devCountService.modDevCount(devCountDTO);
		} catch (Exception e) {
			log.error("修改清分机信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO delDevCountInfoByNo(String devNo){
		log.info("------------[delDevCountInfoByNo]DevCountInnerResource-------------");
		try {
			if (StringUtil.isNullorEmpty(devNo)){
				return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
			}
			return devCountService.delDevCountByNo(devNo);
		}catch (Exception e){
			log.error("删除清分机信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public ListDTO<DevCountDTO> qryDevConMonitoring(Map<String, Object> paramMap) {
		ListDTO<DevCountDTO> dto;
		try {
			dto = devCountService.queryDevConMonitoring(paramMap);

		} catch (Exception e) {
			log.error("查询清分机状态监控信息失败", e);
			dto = new ListDTO<>(RetCodeEnum.FAIL);
		}
		return dto;

	}

	@Override
	public ListDTO<DevCountDTO> qryCountTaskNum() {
		ListDTO<DevCountDTO> dto;
		try {
			dto = devCountService.qryCountTaskNum();

		} catch (Exception e) {
			log.error("查询清分机正在执行情况失败", e);
			dto = new ListDTO<>(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public DTO allotCount(CountTaskInfoDTO countTaskInfoDTO){
		log.info("------------[allotCount]DevCountInnerResource-------------");
		try {
			/*String devNo = devCountDTO.getDevNo();
			if (StringUtil.isNullorEmpty(devNo)){
				return new DTO(RetCodeEnum.FAIL);
			}else if (devCountService.queryByDevNo(devNo)>0){
				log.error("添加失败，设备编号已存在");
				return new DTO(RetCodeEnum.FAIL.getCode(),"添加失败，设备编号已存在");
			}*/
			return devCountService.allotCount(countTaskInfoDTO);
		}catch (Exception e){
			log.error("分配清分机异常：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 配钞信息查询：查询出符合需要的清分设备
	 */
	@Override
	public ListDTO<DevCountDTO> qryCountDevList(Map<String, Object> paramMap){
		try {
			return devCountService.qryCountDevList(paramMap);
		} catch (Exception e) {
			log.error("[查询清分设备信息异常]", e);
			return new ListDTO(RetCodeEnum.FAIL);
		}
	}


}
