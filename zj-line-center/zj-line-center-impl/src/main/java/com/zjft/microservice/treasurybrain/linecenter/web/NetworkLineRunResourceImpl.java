package com.zjft.microservice.treasurybrain.linecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineWorkTableDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.NetworkLineRunInfoDTO;
import com.zjft.microservice.treasurybrain.linecenter.service.NetworkLineRunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zhangjs
 * @since 2019/8/28 18:51
 */
@Slf4j
@RestController
public class NetworkLineRunResourceImpl implements NetworkLineRunResource {

	@Resource
	private NetworkLineRunService networkLineRunService;

	@Override
	public PageDTO<NetworkLineRunInfoDTO> qryNetworkLineRunMap(Map<String, Object> paramMap) {
		PageDTO<NetworkLineRunInfoDTO> dto = new PageDTO<NetworkLineRunInfoDTO>();
		try{String clrCenterNo = StringUtil.parseString(paramMap.get("clrCenterNo"));
			String endMonth = StringUtil.parseString(paramMap.get("endMonth"));
			String networkLineNo = StringUtil.parseString(paramMap.get("networkLineNo"));
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
			params.put("networkLineNo", networkLineNo);
			params.put("startMonth", startMonth);
			params.put("curPage", curPage);
			params.put("pageSize", pageSize);

			Map<String, Object> aMap = networkLineRunService.qryNetworkLineRunMap(JSONUtil.createJsonString(params));

			List<NetworkLineRunInfoDTO> retList = (List<NetworkLineRunInfoDTO>)aMap.get("retList");
			dto.setTotalRow(StringUtil.ch2Int(String.valueOf(aMap.get("totalRow"))));
			dto.setTotalPage(StringUtil.ch2Int(String.valueOf(aMap.get("totalPage"))));
			dto.setRetCode(StringUtil.parseString(aMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(aMap.get("retMsg")));
			dto.setPageSize(pageSize);
			dto.setCurPage(curPage);
			dto.setRetList(retList);

		}catch (Exception e){
			log.error("查询网点线路运行图失败", e);
			dto.setRetException("查询网点线路运行图失败");
		}

		return dto;
	}

	@Override
	public DTO addNetworkLineRunMap(LineWorkTableDTO lineWorkTableDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"添加网点线路运行图失败");
		try{
		 String lineNo =  StringUtil.parseString(lineWorkTableDTO.getLineNo());
		 String clrCenterNo =  StringUtil.parseString(lineWorkTableDTO.getClrCenterNo());
		 String theYearMonth =  StringUtil.parseString(lineWorkTableDTO.getTheYearMonth());

			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("theYearMonth", theYearMonth);
			params.put("lineNo", lineNo);
			params.put("clrCenterNo", clrCenterNo);
			Map<String,Object> retMap = networkLineRunService.addNetworkLineRunMap(JSONUtil.createJsonString(params));
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));

		}catch(Exception e)
		{
			log.error("添加网点线路运行图失败", e);
			dto.setRetException("添加网点线路运行图失败!");
		}
		return dto;
	}

	@Override
	public DTO modNetworkLineRunMap(LineWorkTableDTO lineWorkTableDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL.getCode(),"修改网点线路运行图失败");
		try
		{ Map<String,Object> retMap = networkLineRunService.modNetworkLineRunMap(lineWorkTableDTO);
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
		}
		catch (Exception e )
		{log.error("修改网点线路运行图失败", e);
			dto.setRetException("修改网点线路运行图失败!");

		}
		return dto;
	}

	@Override
	public ObjectDTO qrydetailNetworkLineRunMap(Map<String, Object> paramMap) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(),"查询网点线路运行图详情失败");
		try{
			HashMap<String, Object> params = new HashMap<String, Object>();
			String networkLineNo = StringUtil.parseString(paramMap.get("networkLineNo"));
			String theYearMonth = StringUtil.parseString(paramMap.get("theYearMonth"));
			params.put("networkLineNo",networkLineNo);
			params.put("theYearMonth",theYearMonth);
			Map<String,Object> retMap = networkLineRunService.qrydetailNetworkLineRunMap(JSONUtil.createJsonString(params));
			List<NetworkLineRunInfoDTO> retList = (List<NetworkLineRunInfoDTO>)retMap.get("retList");
			dto.setRetCode(StringUtil.parseString(retMap.get("retCode")));
			dto.setRetMsg(StringUtil.parseString(retMap.get("retMsg")));
			dto.setElement(retList);
		}
		catch (Exception e)
		{
			log.error("查询网点线路运行图详情失败", e);
			dto.setRetException("查询网点线路运行图详情失败!");
		}
		return dto;
	}
}
