package com.zjft.microservice.treasurybrain.linecenter.web_inner;

import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.service.AddnoteLineInnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 常 健
 * @since 2019/12/31
 */

@Slf4j
@RestController
public class AddnoteLineInnerResourceImpl implements AddnoteLineInnerResource {

	@Resource
	private AddnoteLineInnerService addnoteLineInnerService;

	@Override
	public List<String> qryLineHasMapWithDate(Map<String, Object> map) {
		return addnoteLineInnerService.qryLineHasMapWithDate(map);
	}

	@Override
	public String qryLineNameByLineNo(String lineNo) {
		return addnoteLineInnerService.qryLineNameByLineNo(lineNo);
	}

	@Override
	public int deleteByPrimaryKey(String lineNo) {
		return addnoteLineInnerService.deleteByPrimaryKey(lineNo);
	}

	@Override
	public int insert(Map<String, Object> map) {
		return addnoteLineInnerService.insert(map);
	}

	@Override
	public LineTableDTO selectByPrimaryKey(String lineNo) {
		return addnoteLineInnerService.selectByPrimaryKey(lineNo);
	}

	@Override
	public List<Map<String, Object>> qryClrCenter(String lineNo) {
		return addnoteLineInnerService.qryClrCenter(lineNo);
	}

	@Override
	public int updateByMap(Map<String, Object> map) {
		return addnoteLineInnerService.updateByMap(map);
	}

	@Override
	public List<LineTableDTO> qryLineListByMap(Map<String, Object> paramMap) {
		return addnoteLineInnerService.qryLineListByMap(paramMap);
	}

	@Override
	public List<LineTableDTO> getLineListByDateAndClrNo(Map<String, Object> paramMap) {
		return addnoteLineInnerService.getLineListByDateAndClrNo(paramMap);
	}

	@Override
	public List<LineTableDTO> qryAllInfo() {
		return addnoteLineInnerService.qryAllInfo();
	}

	@Override
	public int qryTotalRowPlan(Map<String, Object> paramMap) {
		return addnoteLineInnerService.qryTotalRowPlan(paramMap);
	}

	@Override
	public List<LineTableDTO> rowSetList(Map<String, Object> paramMap) {
		return addnoteLineInnerService.rowSetList(paramMap);
	}

	@Override
	public List<LineTableDTO> rowSetList1(Map<String, Object> paramMap) {
		return addnoteLineInnerService.rowSetList1(paramMap);
	}

	@Override
	public List<String> getLineNosWithTypeAndClrNo(Map<String, Object> paramsMap) {
		return addnoteLineInnerService.getLineNosWithTypeAndClrNo(paramsMap);
	}
}
