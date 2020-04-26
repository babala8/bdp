package com.zjft.microservice.treasurybrain.channelcenter.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.channelcenter.dto.DevCountDTO;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.DevCountConverter;
import com.zjft.microservice.treasurybrain.channelcenter.po.DevCountPO;
import com.zjft.microservice.treasurybrain.channelcenter.repository.DevCountMapper;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 崔耀中
 * @since 2020-01-08
 */
@Slf4j
@ZjComponentResource(group = "channelcenter")
public class DevComponent {

	@Resource
	private DevCountMapper devCountMapper;

	/**
	 * @Description 新增设备信息
	 * @Param
	 */
	@ZjComponentMapping("addDevCountInfo")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String addDevCountInfo(DevCountDTO requestDTO,DTO returnDTO ,List<String> no){
		log.info("------------[addDevCountInfo]DevCountService-------------");
		try {
			DevCountPO devCountPO = DevCountConverter.INSTANCE.dto2domain(requestDTO);
			int insert = devCountMapper.insert(devCountPO);
			if (insert == 1){
				returnDTO.setResult(RetCodeEnum.SUCCEED);
				return "ok";
			}
		}catch (Exception e){
			log.error("[addDevCountInfo] Fail: ", e);
			returnDTO.setResult(RetCodeEnum.EXCEPTION);
			return "fail";
		}
		returnDTO.setResult(RetCodeEnum.FAIL);
		return "fail";
	}


	/**
	 * @Description 删除设备信息
	 * @Param
	 */
	@ZjComponentMapping("delDevCountByNo")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String delDevCountByNo(String devNo, DTO returnDTO,DevCountDTO requestDTO ){
		log.info("------------[delDevCountByNo]DevCountService-------------");
		try {
			int x = devCountMapper.delDevCountByNo(devNo);
			if (x == 1) {
				returnDTO.setResult(RetCodeEnum.SUCCEED);
				return "ok";
			}
		} catch (Exception e) {
			log.error("[delDevCountByNo]Fail", e);
			returnDTO.setResult(RetCodeEnum.EXCEPTION);
			return "fail";
		}
		return "ok";
	}

	/**
	 * @Description 修改设备信息
	 * @Param
	 */
	@ZjComponentMapping("modDevCount")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String modDevCount(DevCountDTO requestDTO,DTO returnDTO ,List<String> no){
		log.info("------------[modDevCount]DevCountService-------------");
		try {
			DevCountPO devCountPO = DevCountConverter.INSTANCE.dto2domain(requestDTO);
			int x = devCountMapper.update(devCountPO);
			if (x == 1) {
				returnDTO.setResult(RetCodeEnum.SUCCEED);
				return "ok";
			}
		} catch (Exception e) {
			log.error("[modDevCount]Fail", e);
			returnDTO.setResult(RetCodeEnum.EXCEPTION);
			return "fail";
		}
		return "ok";
	}

}
