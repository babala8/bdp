package com.zjft.microservice.treasurybrain.datainsight.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefDetailQueryDO;
import com.zjft.microservice.treasurybrain.datainsight.dto.QueryManageDTO;
import com.zjft.microservice.treasurybrain.datainsight.repository.SelfDefDetailQueryMapper;
import com.zjft.microservice.treasurybrain.datainsight.service.DetailQueryManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DetailQueryManageServiceImpl implements DetailQueryManageService {

	private final SelfDefDetailQueryMapper selfDefDetailQueryMapper;

	@Autowired
	public DetailQueryManageServiceImpl(SelfDefDetailQueryMapper selfDefDetailQueryMapper) {
		this.selfDefDetailQueryMapper = selfDefDetailQueryMapper;
	}

	@Override
	public PageDTO qryDetailQuery(Map<String, Object> paramsMap) {
		log.info("------------[qryDetailQuery]queryManageService-------------");
		PageDTO<QueryManageDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		try {
			Integer pageSize = StringUtil.objectToInt(paramsMap.get("pageSize"));
			Integer curPage = StringUtil.objectToInt(paramsMap.get("curPage"));
			String name = StringUtil.parseString(paramsMap.get("name"));
			String startTime = StringUtil.parseString(paramsMap.get("startTime"));
			String endTime = StringUtil.parseString(paramsMap.get("endTime"));

			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("startRow", pageSize * (curPage - 1));
			paramMap.put("endRow", pageSize * curPage);
			paramMap.put("pageSize", pageSize);
			paramMap.put("name", "%" + name + "%");
			paramMap.put("startTime", startTime);
			paramMap.put("endTime", endTime);

			int totalRow = selfDefDetailQueryMapper.qryTotalRowQuery(paramMap);
			int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

			List<SelfDefDetailQueryDO> list = selfDefDetailQueryMapper.qryDetailQuery(paramMap);
			List<QueryManageDTO> detailQueryList = new ArrayList<QueryManageDTO>();

			for (SelfDefDetailQueryDO selfDefDetailQuery : list) {
				QueryManageDTO aQuery = new QueryManageDTO();
				aQuery.setId(selfDefDetailQuery.getId());
				aQuery.setName(selfDefDetailQuery.getName());
				aQuery.setCreator(selfDefDetailQuery.getCreator());
				aQuery.setGroupName(selfDefDetailQuery.getSelfDefGroup().getGroupname());
				aQuery.setLastestModOp(selfDefDetailQuery.getLastestModOp());
				aQuery.setCreateTime(selfDefDetailQuery.getCreateTime());
				aQuery.setLastestModTime(selfDefDetailQuery.getLastestModTime());
				detailQueryList.add(aQuery);
			}

			pageDTO.setRetList(detailQueryList);
			pageDTO.setTotalRow(totalRow);
			pageDTO.setTotalPage(totalPage);
			pageDTO.setPageSize(pageSize);
			pageDTO.setCurPage(curPage);
			pageDTO.setResult(RetCodeEnum.SUCCEED);
			return pageDTO;
		} catch (Exception e) {
			log.error("[qryDetailQuery] Fail:", e);
			pageDTO.setResult(RetCodeEnum.FAIL);
		}
		return pageDTO;
	}

	@Override
	public DTO delQueryById(String no) {
		log.info("------------[delQueryById]queryManageService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			SelfDefDetailQueryDO selfDefDetailQuery = selfDefDetailQueryMapper.selectByPrimaryKey(no);
			if (selfDefDetailQuery == null) {
				return dto;
			}

			int i = selfDefDetailQueryMapper.deleteByPrimaryKey(no);
			if (i != 1) {
				return dto;
			}
			dto.setResult(RetCodeEnum.SUCCEED);
			return dto;
		} catch (Exception e) {
			log.error("[DelRoleById] Fail:", e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}
}
