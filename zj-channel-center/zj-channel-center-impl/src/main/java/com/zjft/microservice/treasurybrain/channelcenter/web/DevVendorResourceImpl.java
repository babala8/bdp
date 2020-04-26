package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevVendorDTO;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevVendorService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 张笑
 * @since 2019-04-04
 */
@Slf4j
@RestController
public class DevVendorResourceImpl implements DevVendorResource {

	@Resource
	private DevVendorService devVendorService;

	/**
	 * 查询设备品牌列表
	 *
	 * @param name 品牌名称
	 * @return PageDTO<SysParamDTO>
	 */
	@Override
	public PageDTO<DevVendorDTO> queryDevVendorList(String name) {
		PageDTO<DevVendorDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		try {
			pageDTO = devVendorService.queryDevVendorList(name.trim());
		} catch (Exception e) {
			log.error("查询设备品牌列表失败", e);
			pageDTO.setResult(RetCodeEnum.FAIL);
		}
		return pageDTO;
	}

	/**
	 * 新增设备品牌
	 *
	 * @param devVendorDTO 设备品牌
	 * @return DTO
	 */
	@Override
	public DTO addDevVendor(DevVendorDTO devVendorDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(devVendorDTO.getName())) {
			try {
				dto = devVendorService.addDevVendor(devVendorDTO);
			} catch (Exception e) {
				log.error("新增设备品牌失败", e);
				dto.setResult(RetCodeEnum.FAIL);
			}
		}
		return dto;
	}

	/**
	 * 修改设备品牌
	 *
	 * @param dto 设备品牌
	 * @return DTO
	 */
	@Override
	public DTO modDevVendor(DevVendorDTO dto) {
		DTO dto1 = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(dto.getNo()) &&!StringUtil.isNullorEmpty(dto.getName())) {
			try {
				dto1 = devVendorService.modDevVendor(dto);
			} catch (Exception e) {
				log.error("修改设备品牌失败", e);
				dto1.setResult(RetCodeEnum.FAIL);
			}
		}
		return dto1;
	}

	/**
	 * 删除设备品牌
	 * 首先判断设备品牌编号是否为空或空格，如果是，则直接返回错误信息，如果不是，则调用service层方法
	 *
	 * @param no 设备品牌编号
	 * @return DTO
	 */
	@Override
	public DTO delDevVendorByNo(String no) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(no)) {
			try {
				dto = devVendorService.delDevVendorByNo(no);
			} catch (Exception e) {
				log.error("删除设备品牌失败", e);
				dto.setResult(RetCodeEnum.FAIL);
			}
		}
		return dto;
	}
}
