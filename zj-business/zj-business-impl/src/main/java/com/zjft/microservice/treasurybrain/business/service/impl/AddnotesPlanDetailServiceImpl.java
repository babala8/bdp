package com.zjft.microservice.treasurybrain.business.service.impl;

import com.zjft.microservice.treasurybrain.business.repository.AddnotesPlanDetailMapper;
import com.zjft.microservice.treasurybrain.business.service.AddnotesPlanDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class AddnotesPlanDetailServiceImpl implements AddnotesPlanDetailService {
	@Resource
	private AddnotesPlanDetailMapper addnotesPlanDetailMapper;

	@Override
	public int deleteGroup(String addnotesPlanNo) {
		return addnotesPlanDetailMapper.deleteGroup(addnotesPlanNo);
	}

	@Override
	public int updateSortNoNull(Map<String, Object> paramMap) {
		return addnotesPlanDetailMapper.updateSortNoNull(paramMap);
	}

	@Override
	public List<String> getDevPointList(Map<String, Object> paramMap) {
		return addnotesPlanDetailMapper.getDevPointList(paramMap);
	}

	@Override
	public int updateSortNoByNetPoint(Map<String, Object> paramMap) {
		return addnotesPlanDetailMapper.updateSortNoByNetPoint(paramMap);
	}

	@Override
	public int updateSortNoByDevNo(Map<String, Object> paramMap) {
		return addnotesPlanDetailMapper.updateSortNoByDevNo(paramMap);
	}

	@Override
	public int updateSortByGroupNo(Map<String, Object> paramMap) {
		return addnotesPlanDetailMapper.updateSortByGroupNo(paramMap);
	}

	@Override
	public List<Map<String, Object>> getNetPointsOrderBySortNo(Map<String, Object> paramMap) {
		return addnotesPlanDetailMapper.getNetPointsOrderBySortNo(paramMap);
	}

	@Override
	public List<Map<String, Object>> getPlanGroupNetpoints(Map<String, Object> paramMap) {
		return addnotesPlanDetailMapper.getPlanGroupNetpoints(paramMap);
	}

	@Override
	public List<Map<String, Object>> getLineMsgList(Map<String, Object> paramMap) {
		return addnotesPlanDetailMapper.getLineMsgList(paramMap);
	}

	@Override
	public List<Map<String, Object>> getDevCountEachGroup(String addnotesPlanNo) {
		return addnotesPlanDetailMapper.getDevCountEachGroup(addnotesPlanNo);
	}

	@Override
	public int getNetCountInGroupByMap(Map<String, Object> paramMap) {
		return addnotesPlanDetailMapper.getNetCountInGroupByMap(paramMap);
	}

	@Override
	public int updateByMapSelective(Map<String, Object> paramMap) {
		return addnotesPlanDetailMapper.updateByMapSelective(paramMap);
	}

	@Override
	public double selectSumAddnoteAmount(String addnotesPlanNo) {
		return addnotesPlanDetailMapper.selectSumAddnoteAmount(addnotesPlanNo);
	}

}
