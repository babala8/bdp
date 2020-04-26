package com.zjft.microservice.treasurybrain.datainsight.service.impl;

import com.zjft.microservice.treasurybrain.common.domain.SysOrg;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.SysOrgDTO;
import com.zjft.microservice.treasurybrain.common.mapstruct.SysOrgConverter;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.datainsight.repository.SysOrgMapper;
import com.zjft.microservice.treasurybrain.datainsight.service.GisBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 张笑
 */
@Slf4j
@Service
public class GisBusinessServiceImpl implements GisBusinessService {

	@Resource(name = "sysOrgMapperGisBusiness")
	private final SysOrgMapper orgTableMapper;

	public GisBusinessServiceImpl(SysOrgMapper orgTableMapper) {
		this.orgTableMapper = orgTableMapper;
	}

	/**
	 * 需求：地图中查询网点
	 * 思路：先调用Dao层的方法，根据Mapper映射关系在xml文件中找到SQL语句，再执行SQL查询语句，
	 * 返回查询到的结果（包含网点信息的列表）到retList；
	 * 然后遍历存放查询结果的列表对象retList，将该列表对象中的数据取出来，存放到一个新的List对象retAtmDevList中，
	 * 遍历结束后，将新的List对象和返回码、返回信息存放在一个Map对象中。
	 *
	 * @return ListDTO
	 */
	@Override
	public ListDTO qryPointInMap() {
		ListDTO<Map<String, Object>> dto = new ListDTO<>(RetCodeEnum.FAIL);
		try {
			log.info("------------[GisBusinessService]qryPointInMap------------");
			List<Map<String, Object>> retList = orgTableMapper.qryOrgDevsForMap();
			List<Map<String, Object>> tmpList = new ArrayList<>();
			for (Map<String, Object> map : retList) {
				Map<String, Object> map1 = new HashMap<>();
				map1.put("city", map.get("CITY"));
				map1.put("orgNums", map.get("ORGNUMS"));
				map1.put("devNums", map.get("DEVNUMS"));
				tmpList.add(map1);
			}
			dto.setRetList(tmpList);
			dto.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("[qryPointInMap] error" + e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	/**
	 * 需求：根据城市名查询网点列表
	 * 思路：首先判断输入的城市名称是否为空，若为空，则直接返回null，
	 * 若城市名称不为空，则先调用Dao层方法根据Mapper映射关系找到指定的SQL语句，执行SQL语句，返回结果；
	 * 方法执行完毕后，将查询结果存储在List对象中，再将该对象和返回码、返回信息存储在一个Map对象中。
	 *
	 * @param cityName 城市名称
	 * @return ListDTO 网点
	 */
	@Override
	public ListDTO qryPointsByCityName(String cityName) {
		ListDTO<Map<String, Object>> dto = new ListDTO<>(RetCodeEnum.FAIL);
		try {
			log.info("------------[GisBusinessService]qryPointsByCityName------------");

			List<Map<String, Object>> list = orgTableMapper.qryOrgListByCityName(cityName);
			dto.setRetList(list);
			dto.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("[qryPointsByCityName] error" + e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

}
