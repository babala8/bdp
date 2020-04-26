package com.zjft.microservice.treasurybrain.channelcenter.service.impl;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevVendorDTO;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.DevVendorConverter;
import com.zjft.microservice.treasurybrain.channelcenter.repository.DevVendorTableMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevVendorService;
import com.zjft.microservice.treasurybrain.common.domain.DevVendorTable;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备品牌
 *
 * @author 张笑
 * @since 2019-04-04
 */
@Slf4j
@Service
public class DevVendorServiceImpl implements DevVendorService {

	@Resource
	private DevVendorTableMapper devVendorTableMapper;

	/**
	 * 模糊查询设备品牌列表
	 * 暂时不需要分页
	 *
	 * @param name 品牌名称
	 * @return PageDTO<DevVendorDTO>
	 */
	@Override
	public PageDTO<DevVendorDTO> queryDevVendorList(String name) {
		log.info("----------[DevVendorService]queryDevVendorList----------------");
		PageDTO<DevVendorDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		List<DevVendorTable> devVendorTables = devVendorTableMapper.queryDevVendorListFuzzyByName(name);
		List<DevVendorDTO> devVendorDTOS = DevVendorConverter.INSTANCE.domain2dto(devVendorTables);
		pageDTO.setPageSize(devVendorDTOS.size());
		pageDTO.setRetList(devVendorDTOS);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Override
	public DTO addDevVendor(DevVendorDTO devVendorDTO) {
		log.info("----------[DevVendorService]addDevVendor------------");
		devVendorDTO.setNo(devVendorTableMapper.getMax1DevVendorNo());
		DevVendorTable devVendorTable = DevVendorConverter.INSTANCE.dto2domain(devVendorDTO);
		int i = devVendorTableMapper.insert(devVendorTable);
		if (i > 0) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO modDevVendor(DevVendorDTO devVendorDTO) {
		log.info("----------[DevVendorService]modDevVendor------------");
		DevVendorTable devVendorTable = DevVendorConverter.INSTANCE.dto2domain(devVendorDTO);
		int i = devVendorTableMapper.updateByPrimaryKey(devVendorTable);
		if (i > 0) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO delDevVendorByNo(String no) {
		log.info("----------[DevVendorService]delDevVendorByNo------------");
		int i = devVendorTableMapper.deleteByPrimaryKey(no);
		if (i > 0) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}
}
