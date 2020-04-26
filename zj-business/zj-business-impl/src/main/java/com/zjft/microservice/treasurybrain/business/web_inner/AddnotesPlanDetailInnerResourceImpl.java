package com.zjft.microservice.treasurybrain.business.web_inner;

import com.zjft.microservice.treasurybrain.business.service.AddnotesPlanDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class AddnotesPlanDetailInnerResourceImpl implements AddnotesPlanDetailInnerResource {
	@Resource
	AddnotesPlanDetailService addnotesPlanDetailService;

	@Override
	public double selectSumAddnoteAmount(String addnotesPlanNo) {
		return addnotesPlanDetailService.selectSumAddnoteAmount(addnotesPlanNo);
	}

	@Override
	public int deleteGroup(String addnotesPlanNo) {
		return addnotesPlanDetailService.deleteGroup(addnotesPlanNo);
	}

	@Override
	public int updateSortNoNull(Map<String, Object> paramMap) {
		return addnotesPlanDetailService.updateSortNoNull(paramMap);
	}

	@Override
	public List<String> getDevPointList(Map<String, Object> paramMap) {
		return addnotesPlanDetailService.getDevPointList(paramMap);
	}

	@Override
	public int updateSortNoByNetPoint(Map<String, Object> paramMap) {
		return addnotesPlanDetailService.updateSortNoByNetPoint(paramMap);
	}

	@Override
	public int updateSortNoByDevNo(Map<String, Object> paramMap) {
		return addnotesPlanDetailService.updateSortNoByDevNo(paramMap);
	}

	@Override
	public int updateSortByGroupNo(Map<String, Object> paramMap) {
		return addnotesPlanDetailService.updateSortByGroupNo(paramMap);
	}

	@Override
	public List<Map<String, Object>> getNetPointsOrderBySortNo(Map<String, Object> paramMap) {
		return addnotesPlanDetailService.getNetPointsOrderBySortNo(paramMap);
	}

	@Override
	public List<Map<String, Object>> getPlanGroupNetpoints(Map<String, Object> paramMap) {
		return addnotesPlanDetailService.getPlanGroupNetpoints(paramMap);
	}

	@Override
	public List<Map<String, Object>> getLineMsgList(Map<String, Object> paramMap) {
		return addnotesPlanDetailService.getLineMsgList(paramMap);
	}

	@Override
	public List<Map<String, Object>> getDevCountEachGroup(String addnotesPlanNo) {
		return addnotesPlanDetailService.getDevCountEachGroup(addnotesPlanNo);
	}

	@Override
	public int getNetCountInGroupByMap(Map<String, Object> paramMap) {
		return addnotesPlanDetailService.getNetCountInGroupByMap(paramMap);
	}

	@Override
	public int updateByMapSelective(Map<String, Object> paramMap) {
		return addnotesPlanDetailService.updateByMapSelective(paramMap);
	}
}
