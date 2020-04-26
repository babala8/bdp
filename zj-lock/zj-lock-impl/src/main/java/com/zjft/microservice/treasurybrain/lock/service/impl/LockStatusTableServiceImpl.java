package com.zjft.microservice.treasurybrain.lock.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.lock.dto.LockStatusTableDTO;
import com.zjft.microservice.treasurybrain.lock.mapstruct.LockStatusTableConverter;
import com.zjft.microservice.treasurybrain.lock.po.LockStatusTablePO;
import com.zjft.microservice.treasurybrain.lock.repository.LockStatusTableMapper;
import com.zjft.microservice.treasurybrain.lock.service.LockStatusTableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *	锁具状态管理
 * @author 韩通
 * @since 2019-06-26
 */
@Slf4j
@Service
public class LockStatusTableServiceImpl implements LockStatusTableService {

	@Resource
	private LockStatusTableMapper lockStatusTableMapper;
	/**
	 * 分页查询锁具状态
	 */
	@Override
	public PageDTO<LockStatusTableDTO> qryLockStatusByPage(Map<String, Object> paramMap) {
		PageDTO<LockStatusTableDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		int totalRow = lockStatusTableMapper.qryTotalRowLock(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize,totalRow);

		List<LockStatusTablePO> list = lockStatusTableMapper.qryLockStatusByPage(paramMap);
		List<LockStatusTableDTO> keyTypelist = LockStatusTableConverter.INSTANCE.domain2dto(list);

		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setRetList(keyTypelist);
		return pageDTO;
	}
}
