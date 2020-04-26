package com.zjft.microservice.treasurybrain.datainsight.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefGroup;
import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefReports;
import com.zjft.microservice.treasurybrain.datainsight.repository.SelfDefGroupMapper;
import com.zjft.microservice.treasurybrain.datainsight.repository.SelfDefReportsMapper;
import com.zjft.microservice.treasurybrain.datainsight.service.ReportService;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ReportServiceImpl implements ReportService {

	private final SelfDefReportsMapper selfDefReportsMapper;
	private final SelfDefGroupMapper selfDefGroupMapper;
	private final SysUserResource sysUserResource;


	@Autowired
	public ReportServiceImpl(SelfDefReportsMapper selfDefReportsMapper, SelfDefGroupMapper selfDefGroupMapper, SysUserResource sysUserResource) {
		this.selfDefReportsMapper = selfDefReportsMapper;
		this.selfDefGroupMapper = selfDefGroupMapper;
		this.sysUserResource = sysUserResource;
	}

	@Override
	public ListDTO queryReports() {
		log.info("----------[ReportService]queryReports----------------");
		ListDTO<Map<String, Object>> listDTO = new ListDTO<>(RetCodeEnum.SUCCEED);
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String userOrgNo = authUser.getOrgNo();

			List<SelfDefReports> list = selfDefReportsMapper.queryReportsByOrgNo(userOrgNo);
			List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
			for (SelfDefReports selfDefReport : list) {
				Map<String, Object> entity = new HashMap<String, Object>();
				entity.put("reportName", selfDefReport.getName());
				entity.put("fileName", selfDefReport.getFilename());
				entity.put("groupName", selfDefReport.getSelfDefGroup().getGroupname());
				entity.put("createTime", selfDefReport.getCreateTime());
				entity.put("parameters", selfDefReport.getParameters());
				retList.add(entity);
			}
			listDTO.setRetList(retList);
			return listDTO;
		} catch (Exception e) {
			log.error("[queryReports] Fail:" + e);
			return new ListDTO(RetCodeEnum.FAIL);
		}
	}

	@Override
	public PageDTO queryReportsByPage(Map<String, Object> paramsMap) {
		log.info("----------[ReportService]queryReportsByPage----------------");
		PageDTO<Map<String, Object>> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();
			String userOrgNo = authUser.getOrgNo();

			String reportName = StringUtil.parseString(paramsMap.get("reportName"));
			Integer pageSize = StringUtil.objectToInt(paramsMap.get("pageSize"));
			Integer curPage = StringUtil.objectToInt(paramsMap.get("curPage"));

			Map<String, Object> params = new HashMap<>();
			params.put("startRow", pageSize * (curPage - 1));
			params.put("endRow", pageSize * curPage);
			params.put("pageSize", pageSize);
			params.put("userOrgNo", userOrgNo);
			params.put("reportName", "%" + reportName + "%");
			int totalRow = selfDefReportsMapper.qryTotalRowReports(params);
			int totalPage = (int) Math.ceil((double) totalRow / (double) pageSize);

			List<SelfDefReports> resultlist = selfDefReportsMapper.queryReportsByPage(params);

			List<Map<String, Object>> retList = new ArrayList<>();
			for (SelfDefReports selfDefReport : resultlist) {
				Map<String, Object> entity = new HashMap<>();
				entity.put("id", selfDefReport.getId());
				entity.put("reportName", selfDefReport.getName());
				entity.put("fileName", selfDefReport.getFilename());
				entity.put("groupId", selfDefReport.getGroupid());
				entity.put("groupName", selfDefReport.getSelfDefGroup().getGroupname());
				entity.put("creator", selfDefReport.getCreator());
				entity.put("parameters", selfDefReport.getParameters());
				entity.put("createTime", selfDefReport.getCreateTime());
				retList.add(entity);
			}
			pageDTO.setCurPage(curPage);
			pageDTO.setPageSize(pageSize);
			pageDTO.setTotalPage(totalPage);
			pageDTO.setTotalRow(totalRow);
			pageDTO.setRetList(retList);
			return pageDTO;
		} catch (Exception e) {
			log.error("[queryReportsByPage] Fail: ", e);
			return new PageDTO(RetCodeEnum.FAIL);
		}
	}

	@Override
	public DTO delReport(String no) {
		log.info("------------[delReportById]delReport-------------");
		try {
			if (selfDefReportsMapper.deleteByPrimaryKey(no) == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
			return new DTO(RetCodeEnum.FAIL);
		} catch (Exception e) {
			log.error("[delReport] Fail: ", e);
			return new DTO(RetCodeEnum.FAIL);
		}
	}

	@Override
	public DTO addReport(Map<String, Object> reqParams) {
		log.info("------------[delReportById]addReport-------------");
		try {
			JSONObject jsonObject = new JSONObject(reqParams);
			UserDTO authUser = sysUserResource.getAuthUserInfo();

			String orgNo = authUser.getOrgNo();
			String creator = authUser.getUsername();
			String id = jsonObject.getString("id");
			String reportName = jsonObject.getString("reportName");
			String groupName = jsonObject.getString("groupName");
			String fileName = jsonObject.getString("fileName");

			JSONArray parameters = jsonObject.getJSONArray("parameters");

			SelfDefReports selfDefReports = new SelfDefReports();
			selfDefReports.setId(id);
			selfDefReports.setName(reportName);
			selfDefReports.setFilename(fileName);
			selfDefReports.setParameters(JSONUtil.createJsonString(parameters));

			selfDefReports.setCreator(creator);
			selfDefReports.setCreatorOrgno(orgNo);
			selfDefReports.setCreateTime(CalendarUtil.getSysTimeYMDHMS());
			selfDefReports.setLastestModOp(creator);
			selfDefReports.setLastestModTime(CalendarUtil.getSysTimeYMDHMS());
			selfDefReports.setStatus(1);

			//groupId 创建，如已有同名groupName则使用已有groupId
			int groupId = selfDefGroupMapper.getMaxGroupIdByGroupName(groupName);
			if (groupId != 0) {
				selfDefReports.setGroupid(groupId);
			} else {
				groupId = selfDefGroupMapper.getMaxGroupId() + 1;
				selfDefReports.setGroupid(groupId);
				SelfDefGroup selfDefGroup = new SelfDefGroup();
				selfDefGroup.setGroupid(groupId);
				selfDefGroup.setGroupname(groupName);
				selfDefGroupMapper.insert(selfDefGroup);
			}

			int i = selfDefReportsMapper.insertOrUpdate(selfDefReports);
			if (i == 1) {
				return new DTO(RetCodeEnum.SUCCEED);
			}
			return new DTO(RetCodeEnum.FAIL);
		} catch (Exception e) {
			log.error("[addReport] Fail: ", e);
			return new DTO(RetCodeEnum.FAIL);
		}
	}

	@Override
	public ListDTO queryGroups() {
		log.info("----------[ReportService]queryGroups----------------");
		ListDTO<Map<String, Object>> listDTO = new ListDTO<>(RetCodeEnum.FAIL);
		try {
			List<SelfDefGroup> list = selfDefGroupMapper.queryGroups();
			List<Map<String, Object>> mapList = new ArrayList<>();
			for (SelfDefGroup g : list) {
				Map<String, Object> map = new HashMap<>(list.size());
				map.put("groupid", g.getGroupid());
				map.put("groupname", g.getGroupname());
				mapList.add(map);
			}
			listDTO.setResult(RetCodeEnum.SUCCEED);
			listDTO.setRetList(mapList);
			return listDTO;
		} catch (Exception e) {
			log.error("[queryGroups] Fail: ", e);
			return new ListDTO(RetCodeEnum.FAIL);
		}
	}
}
