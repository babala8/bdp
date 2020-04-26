package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.service.DevLineRunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author
 * ,
 */
@Slf4j
@RestController
public class DevLineRunResourceImpl implements DevLineRunResource {

	@Resource
	private DevLineRunService devLineRunService;

	@Override
	public PageDTO<LineWorkTableDTO> qryLineRunMap(Map<String, Object> paramMap) {
		PageDTO<LineWorkTableDTO> dto = new PageDTO<LineWorkTableDTO>();
		try{String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			String endMonth = StringUtil.parseString(paramMap.get("endMonth"));
			String lineNo = StringUtil.parseString(paramMap.get("lineNo"));
			String startMonth = StringUtil.parseString(paramMap.get("startMonth"));

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
			params.put("endMonth", endMonth);
			params.put("lineNo", lineNo);
			params.put("startMonth", startMonth);
			params.put("curPage", curPage);
			params.put("pageSize", pageSize);

			Map<String, Object> aMap = devLineRunService.qryLineRunMap(JSONUtil.createJsonString(params));

			List<LineWorkTableDTO> retList = (List<LineWorkTableDTO>)aMap.get("retList");
			dto.setTotalRow(StringUtil.ch2Int(String.valueOf(aMap.get("totalRow"))));
			dto.setTotalPage(StringUtil.ch2Int(String.valueOf(aMap.get("totalPage"))));
			dto.setRetCode(StringUtil.parseString(aMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(aMap.get("retMsg")));
			dto.setPageSize(pageSize);
			dto.setCurPage(curPage);
			dto.setRetList(retList);

		}catch (Exception e){
			log.error("查询失败", e);
			dto.setRetException("查询失败");
		}

		return dto;
	}

	@Override
	public DTO addLineRunMap(LineWorkTableDTO lineWorkTableDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"添加失败");
		try{
         String lineNo =  StringUtil.parseString(lineWorkTableDTO.getLineNo());
         String clrCenterNo =  StringUtil.parseString(lineWorkTableDTO.getClrCenterNo());
         String theYearMonth =  StringUtil.parseString(lineWorkTableDTO.getTheYearMonth());

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("theYearMonth", theYearMonth);
			params.put("lineNo", lineNo);
			params.put("clrCenterNo", clrCenterNo);
			Map<String,Object> retMap = devLineRunService.addLineRunMap(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));

		}catch(Exception e)
		{
			log.error("添加失败", e);
			dto.setRetException("添加失败!");
		}
		return dto;
	}

	@Override
	public DTO modLineRunMap(LineWorkTableDTO lineWorkTableDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"修改失败");
		try
		{ Map<String,Object> retMap = devLineRunService.modLineRunMap(lineWorkTableDTO);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		}
		catch (Exception e )
		{log.error("修改失败", e);
			dto.setRetException("修改失败!");

		}
		return dto;
	}

	@Override
	public ObjectDTO detailLineRunMap(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(),"查询详情失败");
		try{
			HashMap<String, Object> params = new HashMap<String, Object>();
			String lineNo = StringUtil.parseString(paramMap.get("lineNo"));
			String theYearMonth = StringUtil.parseString(paramMap.get("theYearMonth"));
			params.put("lineNo",lineNo);
			params.put("theYearMonth",theYearMonth);
			Map<String,Object> retMap = devLineRunService.detailLineRunMap(JSONUtil.createJsonString(params));
			List<LineWorkTableDTO> retList = (List<LineWorkTableDTO>)retMap.get("retList");
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retList);
		}
		catch (Exception e)
		{
			log.error("查询详情失败", e);
			dto.setRetException("查询详情失败!");
		}
		return dto;
	}

	@Override
	public ObjectDTO getLineRoute(List<String> devList){
		log.info("------------[getLineRoute]lineRunService-------------");
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), RetCodeEnum.FAIL.getTip());
		try {
			Map<String, Object> retMap = devLineRunService.getLineRoute(devList);
			dto.setElement(retMap);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		}catch (Exception e){
			log.error("查询路线失败:", e);
			dto.setRetException("查询路线失败:");
			return dto;
		}
		return dto;
	}

}
