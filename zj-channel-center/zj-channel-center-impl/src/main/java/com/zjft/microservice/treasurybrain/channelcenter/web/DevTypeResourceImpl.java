package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevTypeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevTypeService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 张笑
 * @since 2019-04-08
 */
@Slf4j
@RestController
public class DevTypeResourceImpl implements DevTypeResource {

	@Resource
	private DevTypeService devTypeService;

	/**
	 * 查询设备型号列表
	 *
	 * @param name 型号名称
	 * @return PageDTO<DevTypeDTO>
	 */
	@Override
	public PageDTO<DevTypeDTO> queryDevTypeList(String name) {
		PageDTO<DevTypeDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		try {
			pageDTO = devTypeService.queryDevTypeList(name);
		} catch (Exception e) {
			log.error("查询设备型号列表", e);
			pageDTO.setResult(RetCodeEnum.FAIL);
		}
		return pageDTO;
	}

	/**
	 * 新增设备型号<br/>
	 * name devVendor devCatalog 都不能为空
	 *
	 * @param devTypeDTO 设备型号
	 * @return DTO
	 */
	@Override
	public DTO addDevType(DevTypeDTO devTypeDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(devTypeDTO.getName()) && !StringUtil.isNullorEmpty(devTypeDTO.getDevVendor()) && !StringUtil.isNullorEmpty(devTypeDTO.getDevCatalog())) {
			try {
				dto = devTypeService.addDevType(devTypeDTO);
			} catch (Exception e) {
				log.error("新增设备型号失败", e);
			}
		}
		return dto;
	}

	/**
	 * 修改设备型号
	 *
	 * @param devTypeDTO 设备型号
	 * @return DTO
	 */
	@Override
	public DTO modDevType(DevTypeDTO devTypeDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(devTypeDTO.getNo()) && !StringUtil.isNullorEmpty(devTypeDTO.getName())) {
			try {
				dto = devTypeService.modDevType(devTypeDTO);
			} catch (Exception e) {
				log.error("修改设备型号失败", e);
			}
		}
		return dto;
	}

	/**
	 * 删除设备型号
	 *
	 * @param no 设备型号编号
	 * @return DTO
	 */
	@Override
	public DTO delDevTypeByNo(String no) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(no)) {
			try {
				dto = devTypeService.delDevTypeByNo(no);
			} catch (Exception e) {
				log.error("删除设备型号失败", e);
			}
		}
		return dto;
	}
}
