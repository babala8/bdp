package com.zjft.microservice.treasurybrain.pushserver.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.pushserver.dto.PushSeverInfoDTO;
import com.zjft.microservice.treasurybrain.pushserver.mapstruct.PushServerInfoConverter;
import com.zjft.microservice.treasurybrain.pushserver.po.PushServerInfoPO;
import com.zjft.microservice.treasurybrain.pushserver.repository.PushServerInfoMapper;
import com.zjft.microservice.treasurybrain.pushserver.service.PushServerInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/09/26
 */
@Service
@Slf4j
public class PushServerInfoServiceImpl implements PushServerInfoService {

	@Resource
	private PushServerInfoMapper pushServerInfoMapper;

	@Override
	public PageDTO<PushSeverInfoDTO> qryByPage(Map<String, Object> paramMap) {
		PageDTO<PushSeverInfoDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		Integer pageSize = PageUtil.transParam2Page(paramMap, pageDTO);

		int totalRow = pushServerInfoMapper.qryTotalRow(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<PushServerInfoPO> doList = pushServerInfoMapper.queryPushServerInfoByPage(paramMap);
		List<PushSeverInfoDTO> dtoList = PushServerInfoConverter.INSTANCE.domain2dto(doList);

		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setPageSize(dtoList.size());
		pageDTO.setRetList(dtoList);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Override
	public DTO updateStatus(String no) {
		int x = pushServerInfoMapper.updateNoticeFlag(no);
		if (x != 1) {
			log.error("更新状态失败！");
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public List<PushSeverInfoDTO> qryPushInfo() {
		List<PushServerInfoPO> poList = pushServerInfoMapper.qryPushInfo();
		return PushServerInfoConverter.INSTANCE.domain2dto(poList);
	}

	@Override
	public int deletePushServerInfoById(String no) {
		return pushServerInfoMapper.deleteInfoById(no);
	}

	@Override
	public PushSeverInfoDTO qryMessageByNo(String no) {
		PushServerInfoPO pushServerInfoPO = pushServerInfoMapper.qryInfoByNo(no);
		PushSeverInfoDTO pushSeverInfoDTO = PushServerInfoConverter.INSTANCE.domain2dto(pushServerInfoPO);
		pushSeverInfoDTO.setRetCode("00");
		return pushSeverInfoDTO;
	}
}
