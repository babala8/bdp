package com.zjft.microservice.treasurybrain.channelcenter.service.impl;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevTypeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.DevTypeConverter;
import com.zjft.microservice.treasurybrain.channelcenter.repository.DevTypeTableMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevTypeService;
import com.zjft.microservice.treasurybrain.common.domain.DevTypeTable;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 设备类型
 *
 * @author 张笑
 * @since 2019-04-08
 */
@Slf4j
@Service
public class DevTypeServiceImpl implements DevTypeService {

	@Resource
	private DevTypeTableMapper devTypeTableMapper;

	@Override
	public PageDTO<DevTypeDTO> queryDevTypeList(String name) {
		log.info("----------[DevTypeService]queryDevTypeList----------------");
		PageDTO<DevTypeDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		List<DevTypeTable> devTypeTables = devTypeTableMapper.queryDevTypeList(name);
		List<DevTypeDTO> devTypeDTOS = DevTypeConverter.INSTANCE.domain2dto(devTypeTables);
		pageDTO.setPageSize(devTypeDTOS.size());
		pageDTO.setRetList(devTypeDTOS);
		pageDTO.setTotalRow(devTypeTables.size());
		int totalPage = PageUtil.computeTotalPage(devTypeDTOS.size(), devTypeTables.size());
		pageDTO.setTotalPage(totalPage);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Override
	public DTO addDevType(DevTypeDTO devTypeDTO) {
		log.info("----------[DevTypeService]addDevType----------------");
		DevTypeTable devTypeTable = DevTypeConverter.INSTANCE.dto2domain(devTypeDTO);
		devTypeTable.setNo(devTypeTableMapper.getMax1DevTypeNo());
		int i = devTypeTableMapper.insert(devTypeTable);
		if (i > 0) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO modDevType(DevTypeDTO devTypeDTO) {
		log.info("----------[DevTypeService]modDevType----------------");
		DevTypeTable devTypeTable = DevTypeConverter.INSTANCE.dto2domain(devTypeDTO);
		int i = devTypeTableMapper.updateByPrimaryKeySelective(devTypeTable);
		if (i > 0) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO delDevTypeByNo(String no) {
		log.info("----------[DevTypeService]delDevTypeByNo----------------");
		int i = devTypeTableMapper.deleteByPrimaryKey(no);
		if (i > 0) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}
}
