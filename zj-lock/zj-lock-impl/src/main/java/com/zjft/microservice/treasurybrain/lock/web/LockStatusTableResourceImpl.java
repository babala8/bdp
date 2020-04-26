package com.zjft.microservice.treasurybrain.lock.web;

import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.lock.dto.LockStatusTableDTO;
import com.zjft.microservice.treasurybrain.lock.service.LockStatusTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 锁具状态监控
 *
 * @author 韩通
 * @since 2019-06-26
 */
@Slf4j
@RestController
public class LockStatusTableResourceImpl implements LockStatusTableResource{

	@Resource
	private LockStatusTableService lockStatusTableService;

	/**
	 *	分页查询锁具状态
	 */
	@Override
	public PageDTO<LockStatusTableDTO> qryLockStatusByPage(Map<String, Object> paramMap) {
		try {
			return lockStatusTableService.qryLockStatusByPage(paramMap);
		} catch (Exception e) {
			log.error("[分页查询锁具状态异常]: ", e);
			return new PageDTO<>(RetCodeEnum.FAIL);
		}
	}

}
