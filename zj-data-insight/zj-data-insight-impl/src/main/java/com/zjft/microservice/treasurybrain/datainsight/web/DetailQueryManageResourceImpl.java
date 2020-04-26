package com.zjft.microservice.treasurybrain.datainsight.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.dto.QueryManageDTO;
import com.zjft.microservice.treasurybrain.datainsight.service.DetailQueryManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
public class DetailQueryManageResourceImpl implements DetailQueryManageResource {

	private final DetailQueryManageService detailQueryManageService;

	@Autowired
	public DetailQueryManageResourceImpl(DetailQueryManageService detailQueryManageService) {
		this.detailQueryManageService = detailQueryManageService;
	}

	@Override
	public PageDTO qryDetailQuery(Map<String, Object> params) {
		PageDTO dto = new PageDTO(RetCodeEnum.FAIL);
		try {
			int curPage = StringUtil.objectToInt(params.get("curPage"));
			int pageSize = StringUtil.objectToInt(params.get("pageSize"));

			if (-1 == curPage) {
				params.put("curPage", 1);
			}
			if (-1 == pageSize) {
				params.put("pageSize", 20);
			}

			dto = detailQueryManageService.qryDetailQuery(params);

		} catch (Exception e) {
			log.error("查询明细查询信息失败 ", e);
			dto.setRetException("查询明细查询信息失败");
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public DTO delQuery(String no) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		QueryManageDTO model = new QueryManageDTO();
		try {
			dto = detailQueryManageService.delQueryById(no);

		} catch (Exception e) {
			log.error("删除明细查询信息失败 ", e);
			dto.setRetException("删除" + model.getId() + "失败");
		}
		return dto;
	}
}
