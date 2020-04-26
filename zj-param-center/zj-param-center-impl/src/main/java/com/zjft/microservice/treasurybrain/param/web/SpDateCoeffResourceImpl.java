package com.zjft.microservice.treasurybrain.param.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.param.domain.SpdateCoeffKey;
import com.zjft.microservice.treasurybrain.param.dto.SpDateCoeffDTO;
import com.zjft.microservice.treasurybrain.param.service.SpDateCoeffService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 吴朋
 */
@Slf4j
@RestController
public class SpDateCoeffResourceImpl implements SpDateCoeffResource{
	@Resource
	private SpDateCoeffService spDateCoeffService;

	@Override
	public PageDTO<SpDateCoeffDTO> qry (Map<String, Object> paramMap) {
		PageDTO<SpDateCoeffDTO> dto = new PageDTO<SpDateCoeffDTO>();
		dto.setRetCode(RetCodeEnum.FAIL.getCode());
		dto.setRetMsg("查询特殊日期加钞信息失败！");
		try {
			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			String specialDateStart = StringUtil.parseString(paramMap.get("specialDateStart"));
			String specialDateEnd = StringUtil.parseString(paramMap.get("specialDateEnd"));

			Integer curPage = StringUtil.objectToInt(paramMap.get("curPage"));
			Integer pageSize = StringUtil.objectToInt(paramMap.get("pageSize"));

			if(-1==curPage) {
				curPage = 1;
			}
			if(-1==pageSize) {
				pageSize = 20;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("specialDateStart", specialDateStart);
			params.put("specialDateEnd", specialDateEnd);
			params.put("curPage", curPage);
			params.put("pageSize", pageSize);

			Map<String, Object> aMap = spDateCoeffService.qrySpDateCoeff(JSONUtil.createJsonString(params));

			List<SpDateCoeffDTO> retList = (List<SpDateCoeffDTO>)aMap.get("retList");
			dto.setTotalRow(StringUtil.ch2Int(String.valueOf(aMap.get("totalRow"))));
			dto.setTotalPage(StringUtil.ch2Int(String.valueOf(aMap.get("totalPage"))));
			dto.setRetCode(StringUtil.parseString(aMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(aMap.get("retMsg")));
			dto.setPageSize(pageSize);
			dto.setCurPage(curPage);
			dto.setRetList(retList);
		} catch (Exception e) {
			log.error("查询金库特殊日期配钞失败", e);
			dto.setRetException("查询金库特殊日期配钞失败");
		}
		return dto;
	}

	@Override
	public DTO addSpDateCoeff(SpDateCoeffDTO spDateCoeffDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"添加失败");
		try {
			Double addnotesCoeff = spDateCoeffDTO.getAddnotesCoeff();
			String clrCenterNo = spDateCoeffDTO.getClrCenterNo();
			String startDate = spDateCoeffDTO.getStartDate();
			String endDate = spDateCoeffDTO.getEndDate();

			if(addnotesCoeff>2 || addnotesCoeff<=0){
				dto.setRetMsg("配钞系数应在(0,2]之间");
				return dto;
			}
			if(CalendarUtil.getSubDate(startDate, endDate)>0){
				dto.setRetMsg("开始日期不可大于结束日期");
				return dto;
			}
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("addnotesCoeff", addnotesCoeff);

			Map<String,Object> retMap = spDateCoeffService.addSpDateCoeff(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("增加金库特殊日期配钞设置失败", e);
			dto.setRetException("增加金库特殊日期配钞设置失败!");
		}
		return dto;
	}


	@Override
	public DTO delRegionAddnotes( Map<String,Object> paramMap){
		DTO dto = new DTO();
		dto.setRetCode(RetCodeEnum.FAIL.getCode());
		dto.setRetMsg("删除特殊日期加钞信息失败");
		try {
			String clrCenterNo=StringUtil.parseString(paramMap.get("clrCenterNo"));
			String startDate=StringUtil.parseString(paramMap.get("startDate"));
			String endDate=StringUtil.parseString(paramMap.get("endDate"));
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("clrCenterNo", clrCenterNo);
			params.put("startDate", startDate);
			params.put("endDate", endDate);

			Map<String,Object> retMap = spDateCoeffService.delSpDateCoeff(JSONUtil.createJsonString(params));

			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("删除特殊日期配钞信息失败", e);
			dto.setRetException("删除特殊日期配钞信息失败");
		}
		return dto;

	}

	@Override
	public DTO modSpDateCoeff(SpDateCoeffDTO spDateCoeffDTO){
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(),"修改失败");
		try {
			Double addnotesCoeff = spDateCoeffDTO.getAddnotesCoeff();
			String clrCenterNo = spDateCoeffDTO.getClrCenterNo();
			String startDate = spDateCoeffDTO.getStartDate();
			String endDate = spDateCoeffDTO.getEndDate();

			if(addnotesCoeff>2 || addnotesCoeff<=0){
				dto.setRetMsg("配钞系数应在(0,2]之间");
				return dto;
			}

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("addnotesCoeff", addnotesCoeff);
			params.put("clrCenterNo", clrCenterNo);
			params.put("startDate", startDate);
			params.put("endDate", endDate);

			Map<String,Object> retMap =	spDateCoeffService.modSpDateCoeff(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		} catch (Exception e) {
			log.error("修改特殊日期配钞信息失败:", e);
			dto.setRetException("修改特殊日期配钞信息失败!");
		}
		return dto;
	}

	@Override
	public SpDateCoeffDTO qrySpDateCoeffByKey(Map<String, Object> paramMap) {
		SpDateCoeffDTO spDateCoeffDTO = new SpDateCoeffDTO();
		try {
			String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			String startDate = StringUtil.parseString(paramMap.get("startDate"));
			String endDate = StringUtil.parseString(paramMap.get("endDate"));

			SpdateCoeffKey spdateCoeffKey = new SpdateCoeffKey();
			spdateCoeffKey.setClrCenterNo(clrCenterNo);
			spdateCoeffKey.setEndDate(endDate);
			spdateCoeffKey.setStartDate(startDate);

			spDateCoeffDTO = spDateCoeffService.qrySpDateCoeffByKey(spdateCoeffKey);

		} catch (Exception e) {
			log.error("查询金库特殊日期配钞失败", e);
			return spDateCoeffDTO;
		}
		return spDateCoeffDTO;
	}
}
