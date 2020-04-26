package com.zjft.microservice.treasurybrain.datainsight.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefDetailQueryDO;
import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefGroup;
import com.zjft.microservice.treasurybrain.datainsight.repository.SelfDefDetailQueryMapper;
import com.zjft.microservice.treasurybrain.datainsight.repository.SelfDefGroupMapper;
import com.zjft.microservice.treasurybrain.datainsight.service.DetailQueryService;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class DetailQueryServiceImpl implements DetailQueryService {


	private final SelfDefDetailQueryMapper selfDefDetailQueryMapper;
	private final SelfDefGroupMapper selfDefGroupMapper;
	private final SysUserResource sysUserResource;

	@Autowired
	public DetailQueryServiceImpl(SelfDefDetailQueryMapper selfDefDetailQueryMapper, SelfDefGroupMapper selfDefGroupMapper, SysUserResource sysUserResource) {
		this.selfDefDetailQueryMapper = selfDefDetailQueryMapper;
		this.selfDefGroupMapper = selfDefGroupMapper;
		this.sysUserResource = sysUserResource;
	}


	@Override
	public ObjectDTO invokeService(Map<String, String> params) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询失败！");
		try {
			log.info("------------[detailQueryService]invokeService-------------");
			String serviceURL = StringUtil.parseString(params.get("serviceURL"));
			String serviceMethod = StringUtil.parseString(params.get("serviceMethod"));

			String resultJson = "";
			if ("GET".equalsIgnoreCase(serviceMethod)) {
				resultJson = HttpRequestUtil.restfulGet(serviceURL, null);
			} else if ("POST".equalsIgnoreCase(serviceMethod)) {
				resultJson = HttpRequestUtil.restfulPost(serviceURL, null);
			}

			JSONObject jsonObj = JSONUtil.parseJSONObject(resultJson);
			String retCode = jsonObj.getString("retCode");
			String retMsg = jsonObj.getString("retMsg");
			JSONArray dataListJson = jsonObj.getJSONArray("data");

			dto.setElement(dataListJson);
			dto.setRetCode(retCode);
			dto.setRetMsg(retMsg);

		} catch (Exception e) {
			log.error("[qryDataBySql] error:" + e.getMessage(), e);
			dto.setRetCode(RetCodeEnum.EXCEPTION.getCode());
			dto.setRetMsg("查询失败");
		}
		log.info("[invokeService]返回码=[" + dto.getRetCode() + "] 返回信息=[" + dto.getRetMsg() + "]");
		return dto;
	}

	@Override
	public void addDetailQueryModel(Map<String, Object> params) {
		log.info("------------[detailQueryService]addDetailQueryModel-------------");
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();

			String orgNo = authUser.getOrgNo();
			String username = authUser.getUsername();

			SelfDefDetailQueryDO selfDefDetailQuery = new SelfDefDetailQueryDO();
			selfDefDetailQuery.setId(UUID.randomUUID().toString());
			selfDefDetailQuery.setName(StringUtil.parseString(params.get("name")));

			selfDefDetailQuery.setCreator(username);
			selfDefDetailQuery.setCreatorOrgno(orgNo);

			selfDefDetailQuery.setStatus(1);
			selfDefDetailQuery.setCreateTime(CalendarUtil.getSysTimeYMDHMS());
			selfDefDetailQuery.setLastestModOp(username);
			selfDefDetailQuery.setLastestModTime(CalendarUtil.getSysTimeYMDHMS());

			Map<String, Object> chartDefMap = new HashMap<String, Object>();
			chartDefMap.put("sid", params.get("sid"));
			chartDefMap.put("serviceName", params.get("serviceName"));
			chartDefMap.put("serviceMethod", params.get("serviceMethod"));
			chartDefMap.put("serviceURL", params.get("serviceURL"));
			chartDefMap.put("requestParam", params.get("requestParam"));
			chartDefMap.put("returnParam", params.get("returnParam"));

			selfDefDetailQuery.setDef(JSONUtil.createJsonString(chartDefMap));

			//groupId 创建，如已有同名groupName则使用已有groupId
			String groupName = StringUtil.parseString(params.get("groupName"));
			int groupId = selfDefGroupMapper.getMaxGroupIdByGroupName(groupName);
			if (groupId != 0) {
				selfDefDetailQuery.setGroupid(groupId);
			} else {
				groupId = selfDefGroupMapper.getMaxGroupId() + 1;
				selfDefDetailQuery.setGroupid(groupId);
				SelfDefGroup selfDefGroup = new SelfDefGroup();
				selfDefGroup.setGroupid(groupId);
				selfDefGroup.setGroupname(groupName);
				selfDefGroupMapper.insert(selfDefGroup);
			}

			selfDefDetailQueryMapper.insertOrUpdate(selfDefDetailQuery);
		} catch (Exception e) {
			log.error("[addReportModel]异常", e);
		}
	}

	@Override
	public ObjectDTO qrySelfDefDetailById(String id) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL);
		try {
			log.info("----------[detailQueryService]qrySelfDefDetailById----------------");
			SelfDefDetailQueryDO selfDefDetailQuery = selfDefDetailQueryMapper.selectByPrimaryKey(id);

			JSONObject jsonObject = JSONUtil.parseJSONObject(selfDefDetailQuery.getDef());
			Map<String, String> map = new HashMap<String, String>();
			map.put("serviceURL", jsonObject.getString("serviceURL"));
			map.put("serviceMethod", jsonObject.getString("serviceMethod"));
			ObjectDTO objectDTO = invokeService(map);
			Object data = objectDTO.getElement();
			String retCode = objectDTO.getRetCode();
			if (!RetCodeEnum.SUCCEED.getCode().equalsIgnoreCase(retCode)) {
				return dto;
			}

			JSONArray returnParam = new JSONArray();
			Object tempObj = jsonObject.get("returnParam");
			if (tempObj instanceof JSONArray) {
				returnParam = (JSONArray) tempObj;
			} else if (tempObj instanceof JSONObject) {
				returnParam.add(tempObj);
			}

			String name = selfDefDetailQuery.getName();
			String creator = selfDefDetailQuery.getCreator();
			String createTime = selfDefDetailQuery.getCreateTime();
			String creatorOrgno = selfDefDetailQuery.getCreatorOrgno();
			String lastestModTime = selfDefDetailQuery.getLastestModTime();
			String lastestModOp = selfDefDetailQuery.getLastestModOp();

			Map<String, Object> retMap = new HashMap<>();
			retMap.put("returnParam", returnParam);
			retMap.put("data", data);
			retMap.put("name", name);
			retMap.put("creator", creator);
			retMap.put("createTime", createTime);
			retMap.put("creatorOrgno", creatorOrgno);
			retMap.put("lastestModTime", lastestModTime);
			retMap.put("lastestModOp", lastestModOp);

			dto.setElement(retMap);
			dto.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("[qrySelfDefDetailById] error" + e.getMessage(), e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}

	@Override
	public ListDTO getDetails() {
		ListDTO<Map<String, Object>> listDTO = new ListDTO<>(RetCodeEnum.SUCCEED);
		try {
			log.info("----------[detailQueryService]getDetails----------------");
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String orgNo = authUser.getOrgNo();

			List<SelfDefDetailQueryDO> list = selfDefDetailQueryMapper.getDetailsByOrgNo(orgNo);
			List<Map<String, Object>> retList = new ArrayList<>();
			for (SelfDefDetailQueryDO selfDefDetailQuery : list) {
				Map<String, Object> entity = new HashMap<>(3);
				entity.put("id", selfDefDetailQuery.getId());
				entity.put("name", selfDefDetailQuery.getName());
				entity.put("groupName", selfDefDetailQuery.getSelfDefGroup().getGroupname());
				retList.add(entity);
			}
			listDTO.setRetList(retList);
		} catch (Exception e) {
			log.error("[getDetails] Fail: ", e);
			listDTO.setResult(RetCodeEnum.FAIL);
		}
		return listDTO;
	}

	@Override
	public ListDTO queryGroups() {
		ListDTO<Map<String, Object>> listDTO = new ListDTO<>(RetCodeEnum.SUCCEED);
		try {
			log.info("----------[detailQueryService]queryGroups----------------");
			List<SelfDefGroup> list = selfDefGroupMapper.queryGroups();
			List<Map<String, Object>> objects = new ArrayList<>();
			for (SelfDefGroup g : list) {
				Map<String, Object> map = new HashMap<>(list.size());
				map.put("groupid", g.getGroupid());
				map.put("groupname", g.getGroupname());
				objects.add(map);
			}

			listDTO.setRetList(objects);
			return listDTO;
		} catch (Exception e) {
			log.error("[queryGroups] Fail: ", e);
			listDTO.setResult(RetCodeEnum.FAIL);
		}
		return listDTO;
	}
}
