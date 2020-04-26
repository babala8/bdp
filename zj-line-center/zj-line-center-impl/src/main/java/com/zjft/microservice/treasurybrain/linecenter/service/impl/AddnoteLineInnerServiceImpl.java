package com.zjft.microservice.treasurybrain.linecenter.service.impl;

import com.zjft.microservice.treasurybrain.linecenter.domain.LineTableDO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.mapstruct.AddnotesLineConverter;
import com.zjft.microservice.treasurybrain.linecenter.repository.LineAddnoteLineMapper;
import com.zjft.microservice.treasurybrain.linecenter.repository.LineTableMapper;
import com.zjft.microservice.treasurybrain.linecenter.repository.LineWorkMapper;
import com.zjft.microservice.treasurybrain.linecenter.service.AddnoteLineInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/12/31
 */
@Slf4j
@Service
public class AddnoteLineInnerServiceImpl implements AddnoteLineInnerService {

	@Resource
	private LineTableMapper lineTableMapper;

//	@Resource
//	private LineAddnoteLineMapper lineAddnoteLineMapper;

	@Resource
	LineWorkMapper lineWorkMapper;

	@Override
	public List<String> qryLineHasMapWithDate(Map<String, Object> map) {

//		return lineMapper.qryLineHasMapWithDate(map);
		return lineWorkMapper.qryLineHasMapWithDate(map);
	}

	@Override
	public String qryLineNameByLineNo(String lineNo) {

		return lineTableMapper.getLineName(lineNo);
	}

	@Override
	public int deleteByPrimaryKey(String lineNo) {
//		return lineAddnoteLineMapper.deleteByPrimaryKey(lineNo);
		return lineTableMapper.deleteByPrimaryKey(lineNo);
	}

	@Override
	public int insert(Map<String, Object> map) {
//		return lineAddnoteLineMapper.insertbyMap(map);
		return lineTableMapper.insertbyMap(map);
	}

	@Override
	public LineTableDTO selectByPrimaryKey(String lineNo) {
//		LineTableDO addnoteLineDO = lineAddnoteLineMapper.selectByPrimaryKey(lineNo);
		LineTableDO LineTableDO = lineTableMapper.selectByPrimaryKey(lineNo);
		LineTableDTO LineTableDTO = AddnotesLineConverter.INSTANCE.domain2dto(LineTableDO);
		return LineTableDTO;
	}

	@Override
	public List<Map<String, Object>> qryClrCenter(String lineNo) {
//		return lineAddnoteLineMapper.getClrCenter(lineNo);
		return lineTableMapper.getClrCenter(lineNo);
	}

	@Override
	public int updateByMap(Map<String, Object> map) {
//		return lineAddnoteLineMapper.updateByMap(map);
		return lineTableMapper.updateByMap(map);
	}

	@Override
	public List<LineTableDTO> qryLineListByMap(Map<String, Object> paramMap) {
//		List<LineTableDO> doList = lineAddnoteLineMapper.qryLineListByMap(paramMap);
		List<LineTableDO> doList = lineTableMapper.qryLineListByMap(paramMap);
		return AddnotesLineConverter.INSTANCE.domain2dto(doList);
	}

	@Override
	public List<LineTableDTO> getLineListByDateAndClrNo(Map<String, Object> paramMap) {
//		List<LineTableDO> doList = lineAddnoteLineMapper.getLineListByDateAndClrNo(paramMap);
		List<LineTableDO> doList = lineTableMapper.getLineListByDateAndClrNo(paramMap);
		return AddnotesLineConverter.INSTANCE.domain2dto(doList);
	}

	@Override
	public List<LineTableDTO> qryAllInfo() {
//		List<LineTableDO> doList = lineAddnoteLineMapper.getAll();
		List<LineTableDO> doList = lineTableMapper.getAll();
		return AddnotesLineConverter.INSTANCE.domain2dto(doList);
	}

	@Override
	public int qryTotalRowPlan(Map<String, Object> paramMap) {
//		return lineAddnoteLineMapper.qryTotalRowPlan(paramMap);
		return lineTableMapper.qryTotalRowPlan(paramMap);
	}

	@Override
	public List<LineTableDTO> rowSetList(Map<String, Object> paramMap) {
//		List<LineTableDO> doList = lineAddnoteLineMapper.rowSetList(paramMap);
		List<LineTableDO> doList = lineTableMapper.rowSetList(paramMap);
		return AddnotesLineConverter.INSTANCE.domain2dto(doList);
	}

	@Override
	public List<LineTableDTO> rowSetList1(Map<String, Object> paramMap) {
//		List<LineTableDO> doList = lineAddnoteLineMapper.rowSetList1(paramMap);
		List<LineTableDO> doList = lineTableMapper.rowSetList1(paramMap);
		return AddnotesLineConverter.INSTANCE.domain2dto(doList);
	}

	@Override
	public List<String> getLineNosWithTypeAndClrNo(Map<String, Object> paramsMap) {
//		return lineAddnoteLineMapper.getLineNosWithTypeAndClrNo(paramsMap);
		return lineTableMapper.getLineNosWithTypeAndClrNo(paramsMap);
	}
}
