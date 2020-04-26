package com.zjft.microservice.treasurybrain.lock.web;

import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.lock.dto.LockTransTableDTO;
import com.zjft.microservice.treasurybrain.lock.service.LockTransTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 锁具日志查询
 *
 * @author 韩通
 * @since 2019-06-26
 */
@Slf4j
@RestController
public class LockTransTableResourceImpl implements LockTransTableResource{

	@Resource
	private LockTransTableService lockTransTableService;
	/**
	 *	分页查询锁具状态
	 */
	@Override
	public PageDTO<LockTransTableDTO> qryLockTransByPage(Map<String, Object> paramMap) {
		try {
			return lockTransTableService.qryLockTransByPage(paramMap);
		} catch (Exception e) {
			log.error("[分页查询锁具日志异常]: ", e);
			return new PageDTO<>(RetCodeEnum.FAIL);
		}
	}


}
