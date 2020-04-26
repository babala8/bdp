package com.zjft.microservice.treasurybrain.lock.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.lock.dto.LockBaseInfoDTO;
import com.zjft.microservice.treasurybrain.lock.service.LockBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 锁具信息管理
 *
 * @author 常建
 * @since 2019-06-26
 */
@Slf4j
@RestController
public class LockBaseInfoResourceImpl implements LockBaseInfoResource {


	@Resource
	private LockBaseInfoService lockBaseInfoService;

	/**
	 * 新增锁具信息
	 */
	@Override
	public DTO addLockBaseInfo(LockBaseInfoDTO lockBaseInfoDTO) {
		log.info("------------[addLockBaseInfo]LockBaseInfoResource-------------");
		try {
			String lockCode = lockBaseInfoDTO.getLockCode();
			Integer state = lockBaseInfoDTO.getState();
			if (StringUtil.isNullorEmpty(lockCode) || StringUtil.isNullorEmpty(StringUtil.parseString(state))) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			} else if (lockBaseInfoService.qryExistByLockCode(lockCode) > 0) {
				log.error("锁具序列号已存在，请勿重复输入！");
				return new DTO(RetCodeEnum.AUDIT_OBJECT_EXIST);
			}
			return lockBaseInfoService.addLockBaseInfo(lockBaseInfoDTO);
		} catch (Exception e) {
			log.error("新增锁具信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 分页查询锁具信息
	 */
	@Override
	public PageDTO<LockBaseInfoDTO> qryLockBaseInfoByPage(Map<String, Object> paramMap) {
		log.info("------------[qryLockBaseInfoByPage]LockBaseInfoResource-------------");
		try {
			return lockBaseInfoService.qryLockBaseInfoByPage(paramMap);
		} catch (Exception e) {
			log.error("分页查询锁具信息失败", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 修改锁具信息
	 */
	@Override
	public DTO modLockBaseInfo(LockBaseInfoDTO lockBaseInfoDTO) {
		log.info("------------[modLockBaseInfo]LockBaseInfoResource-------------");
		try {
			String lockCode = lockBaseInfoDTO.getLockCode();
			if (StringUtil.isNullorEmpty(lockCode)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			} else if (lockBaseInfoService.qryExistByLockCode(lockCode) < 1) {
				log.error("锁具不存在，请重新输入序列号!");
				return new DTO(RetCodeEnum.FAIL);
			}
			return lockBaseInfoService.modLockBaseInfo(lockBaseInfoDTO);
		} catch (Exception e) {
			log.error("修改锁具信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 删除锁具信息
	 */
	@Override
	public DTO delLockBaseInfo(String lockCode) {
		log.info("------------[delLockBaseInfo]LockBaseInfoResource-------------");
		try {
			if (StringUtil.isNullorEmpty(lockCode)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			} else if (lockBaseInfoService.qryExistByLockCode(lockCode) != 1) {
				log.error("锁具不存在，请重新输入序列号!");
				return new DTO(RetCodeEnum.FAIL);
			}
			return lockBaseInfoService.delLockBaseInfo(lockCode);
		} catch (Exception e) {
			log.error("删除锁具信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	/**
	 * 查询锁具信息详情
	 */
	@Override
	public LockBaseInfoDTO qryLockBaseInfoDetail(String lockCode) {
		log.info("------------[qryLockBaseInfoDetail]LockBaseInfoResource-------------");
		try {
			if (StringUtil.isNullorEmpty(lockCode)) {
				return new LockBaseInfoDTO(RetCodeEnum.PARAM_LACK);
			} else if (lockBaseInfoService.qryExistByLockCode(lockCode) < 1) {
				log.error("锁具不存在，请重新输入有效序列号！");
				return new LockBaseInfoDTO(RetCodeEnum.FAIL);
			}
			return lockBaseInfoService.qryLockBaseInfoDetail(lockCode);
		} catch (Exception e) {
			log.error("详情查询锁具信息失败", e);
			return new LockBaseInfoDTO(RetCodeEnum.EXCEPTION);
		}
	}

}
