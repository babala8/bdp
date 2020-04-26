package com.zjft.microservice.treasurybrain.lock.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.lock.domain.LockBaseInfoDO;
import com.zjft.microservice.treasurybrain.lock.dto.LockBaseInfoDTO;
import com.zjft.microservice.treasurybrain.lock.mapstruct.LockBaseInfoConverter;
import com.zjft.microservice.treasurybrain.lock.repository.LockBaseInfoMapper;
import com.zjft.microservice.treasurybrain.lock.service.LockBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/06/26
 */
@Slf4j
@Service
public class LockBaseInfoServiceImpl implements LockBaseInfoService {

	@Resource
	private LockBaseInfoMapper lockBaseInfoMapper;

	/**
	 * 新增锁具信息详情
	 */
	@Override
	public DTO addLockBaseInfo(LockBaseInfoDTO lockBaseInfoDTO) {
		log.info("------------[addLockBaseInfo]LockBaseInfoService-------------");
		//STATE默认0
		if (lockBaseInfoDTO.getState() != 0) {
			lockBaseInfoDTO.setState(0);
		}
		LockBaseInfoDO lockBaseInfoDO = LockBaseInfoConverter.INSTANCE.dto2domain(lockBaseInfoDTO);
		int x = lockBaseInfoMapper.insert(lockBaseInfoDO);
		if (x == 1) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 分页查询锁具信息
	 */
	@Override
	public PageDTO<LockBaseInfoDTO> qryLockBaseInfoByPage(Map<String, Object> paramMap) {
		log.info("------------[qryLockBaseInfoByPage]LockBaseInfoService-------------");
		PageDTO<LockBaseInfoDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		int totalRow = lockBaseInfoMapper.qryTotalRow(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<LockBaseInfoDO> doList = lockBaseInfoMapper.qryCheckTaskByPage(paramMap);
		List<LockBaseInfoDTO> dtoList = LockBaseInfoConverter.INSTANCE.domain2dto(doList);

		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setRetList(dtoList);
		return pageDTO;
	}

	/**
	 * 修改锁具信息
	 */
	@Override
	public DTO modLockBaseInfo(LockBaseInfoDTO lockBaseInfoDTO) {
		log.info("------------[modLockBaseInfo]LockBaseInfoService-------------");
		LockBaseInfoDO lockBaseInfoDO = LockBaseInfoConverter.INSTANCE.dto2domain(lockBaseInfoDTO);
		int x = lockBaseInfoMapper.update(lockBaseInfoDO);
		if (x == 1) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 删除锁具信息
	 */
	@Override
	public DTO delLockBaseInfo(String lockCode) {
		log.info("------------[delLockBaseInfo]LockBaseInfoService-------------");
		int x = lockBaseInfoMapper.deleteByLockCode(lockCode);
		if (x == 1) {
			return new DTO(RetCodeEnum.SUCCEED);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	/**
	 * 查询锁具信息详情
	 */
	@Override
	public LockBaseInfoDTO qryLockBaseInfoDetail(String lockCode) {
		log.info("------------[qryLockBaseInfoDetail]LockBaseInfoService-------------");
		LockBaseInfoDTO lockBaseInfoDTO = new LockBaseInfoDTO(RetCodeEnum.FAIL);
		try {
			LockBaseInfoDO lockBaseInfoDO = lockBaseInfoMapper.qryLockBaseInfoDetail(lockCode);
			lockBaseInfoDTO = LockBaseInfoConverter.INSTANCE.domain2do(lockBaseInfoDO);
			if (lockBaseInfoDTO != null) {
				lockBaseInfoDTO.setResult(RetCodeEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("详情查询锁具信息失败", e);
			lockBaseInfoDTO.setResult(RetCodeEnum.EXCEPTION);
		}
		return lockBaseInfoDTO;
	}

	/**
	 * 查询锁具是否存在
	 */
	@Override
	public int qryExistByLockCode(String lockCode) {
		log.info("------------[qryLockBaseInfoDetail]LockBaseInfoService-------------");
		return lockBaseInfoMapper.qryExistByLockCode(lockCode);
	}
}
