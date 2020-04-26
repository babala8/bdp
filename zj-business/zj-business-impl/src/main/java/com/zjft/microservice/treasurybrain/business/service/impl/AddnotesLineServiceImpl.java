package com.zjft.microservice.treasurybrain.business.service.impl;


import com.zjft.microservice.treasurybrain.linecenter.dto.LineTableDTO;
import com.zjft.microservice.treasurybrain.business.repository.AddnoteLineMapper;
import com.zjft.microservice.treasurybrain.business.service.AddnotesLineService;
import com.zjft.microservice.treasurybrain.linecenter.web_inner.AddnoteLineInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author
 */
@Slf4j
@Service
public class AddnotesLineServiceImpl implements AddnotesLineService {

	@Resource
	private AddnoteLineInnerResource addnoteLineInnerResource;

	@Override
	public Map<String, String> getAddnotesLineMap() {
//		List<AddnoteLine> addnoteLineListAll = addnoteLineMapper.getAll();
		List<LineTableDTO> addnotesLineDTOList = addnoteLineInnerResource.qryAllInfo();
		Map<String, String> map = new HashMap<String,String>();
		for(int i = 0 ; i < addnotesLineDTOList.size(); i++){
			String lineNo = addnotesLineDTOList.get(i).getLineNo();
			String description = addnotesLineDTOList.get(i).getLineName();
			map.put(lineNo, description);
		}
		return map;
	}

}
