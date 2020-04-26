package com.zjft.microservice.treasurybrain.lock.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.lock.domain.LockTransTableDO;
import com.zjft.microservice.treasurybrain.lock.dto.LockTransTableDTO;
import com.zjft.microservice.treasurybrain.lock.mapstruct.LockTransTableConverter;
import com.zjft.microservice.treasurybrain.lock.repository.LockTransTableMapper;
import com.zjft.microservice.treasurybrain.lock.service.LockTransTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *	锁具日志管理
 * @author 韩通
 * @since 2019-06-26
 */
@Slf4j
@Service
public class LockTransTableServiceImpl implements LockTransTableService {

	@Resource
	private LockTransTableMapper lockTransTableMapper;
	/**
	 * 分页查询锁具日志
	 */
	@Override
	public PageDTO<LockTransTableDTO> qryLockTransByPage(Map<String, Object> paramMap) {
		PageDTO<LockTransTableDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		int totalRow = lockTransTableMapper.qryTotalRowLock(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize,totalRow);

		List<LockTransTableDO> list = lockTransTableMapper.qryLockTransByPage(paramMap);

		List<LockTransTableDTO> keyTypelist = LockTransTableConverter.INSTANCE.domain2dto(list);

		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setRetList(keyTypelist);
		return pageDTO;
	}
}
