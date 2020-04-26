package com.zjft.microservice.treasurybrain.channelcenter.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.business.dto.AddnotesPlanDTO;
import com.zjft.microservice.treasurybrain.business.web_inner.AddnotesPlanInnerResource;
import com.zjft.microservice.treasurybrain.channelcenter.domain.OrgbusinessTimeDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.OrgBusinessTimeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.OrgBusinessTimeConverter;
import com.zjft.microservice.treasurybrain.channelcenter.repository.ClrCenterTableMapper;
import com.zjft.microservice.treasurybrain.channelcenter.repository.OrgBusinessTimeMapper;
import com.zjft.microservice.treasurybrain.channelcenter.repository.SysOrgGradeMapper;
import com.zjft.microservice.treasurybrain.channelcenter.repository.SysOrgMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.ClrCenterService;
import com.zjft.microservice.treasurybrain.channelcenter.service.SysOrgService;
import com.zjft.microservice.treasurybrain.channelcenter.util.OrgNode;
import com.zjft.microservice.treasurybrain.channelcenter.util.TreeBuilder;
import com.zjft.microservice.treasurybrain.common.domain.AddnotesPlan;
import com.zjft.microservice.treasurybrain.common.domain.SysOrg;
import com.zjft.microservice.treasurybrain.common.domain.SysOrgGrade;
import com.zjft.microservice.treasurybrain.common.dto.*;
import com.zjft.microservice.treasurybrain.common.mapstruct.SysOrgConverter;
import com.zjft.microservice.treasurybrain.common.util.JSONUtil;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构管理ServiceImpl
 *
 * @author 张笑
 * @author 杨光
 */
@Slf4j
@Service
public class SysOrgServiceImpl implements SysOrgService {

	@Resource(name = "sysOrgMapperChannel")
	private SysOrgMapper sysOrgMapper;

	@Resource(name = "sysOrgGradeMapperExtend")
	private SysOrgGradeMapper sysOrgGradeMapper;

	@Resource
	private SysUserResource sysUserResource;

	@Resource(name = "orgBusinessTimeMapper")
	private OrgBusinessTimeMapper orgBusinessTimeMapper;

	@Resource
	private ClrCenterService clrCenterService;

	@Resource
	private AddnotesPlanInnerResource addnotesPlanInnerResource;

	@Resource
	private ClrCenterTableMapper clrCenterTableMapper;

	/**
	 * 异步查询机构树 <br/>
	 * 查询用户权限下的下一级机构列表
	 *
	 * @param parentOrgNo 父机构编号
	 * @return ListDTO<SysOrgDTO>
	 */
	@Override
	public ListDTO<SysOrgDTO> qryChildrenOrgByAuth(String parentOrgNo) {
		log.info("----------[SysOrgService]qryChildrenOrgByAuth----------------");
		ListDTO<SysOrgDTO> dto = new ListDTO<>(RetCodeEnum.FAIL);
		UserDTO authUser = sysUserResource.getAuthUserInfo();
		String authOrgNo = authUser.getOrgNo();
		if (StringUtil.isNullorEmpty(parentOrgNo)) {
			parentOrgNo = " NO = '" + authOrgNo + "'";
		} else {
			// 格式化参数，防止sql注入
			Integer.parseInt(parentOrgNo);
			parentOrgNo = " PARENT_ORG = '" + parentOrgNo + "'";
		}
		List<SysOrg> children = sysOrgMapper.getChildrenLevel1(authOrgNo, parentOrgNo);
		List<SysOrgDTO> dtoList = SysOrgConverter.INSTANCE.domain2dto(children);
		// 前端需要这个字段
		for (SysOrgDTO d : dtoList) {
			d.setHasChildren("1");
		}

		dto.setRetList(dtoList);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	/**
	 * 模糊查询机构树
	 *
	 * @param orgName 机构名称
	 * @return ListDTO<SysOrgDTO>
	 */
	@Override
	public ListDTO<SysOrgDTO> qryOrgFuzzyByAuth(String orgName) {
		log.info("----------[SysOrgService]qryOrgFuzzyByAuth----------------");
		ListDTO<SysOrgDTO> dto = new ListDTO<>(RetCodeEnum.FAIL);
		// 获取当前登录用户的信息
		UserDTO authUser = sysUserResource.getAuthUserInfo();
		String authOrgNo = authUser.getOrgNo();
		List<SysOrg> orgList = sysOrgMapper.qryOrgFuzzyByAuth(authOrgNo, orgName);
		List<SysOrgDTO> sysOrgDTOS = SysOrgConverter.INSTANCE.domain2dto(orgList);
		dto.setRetList(sysOrgDTOS);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	/**
	 * 查询机构详情
	 *
	 * @param orgNo 机构编号
	 * @return SysOrgDTO
	 */
	@Override
	public SysOrgDTO qrySysOrgDetailByNo(String orgNo) {
		log.info("----------[qrySysOrgDetailByNo]SysOrgService----------------");
		SysOrgDTO sysOrgDTO = new SysOrgDTO(RetCodeEnum.FAIL);
		if (checkPermissionByOrgNo(orgNo)) {
			SysOrg sysOrg = sysOrgMapper.selectDetailByPrimaryKey(orgNo);
			if (sysOrg != null) {
				sysOrgDTO = SysOrgConverter.INSTANCE.domain2dto(sysOrg);
				sysOrgDTO.setResult(RetCodeEnum.SUCCEED);
			}
		}
		return sysOrgDTO;
	}

	/**
	 * 分页查询机构信息
	 *
	 * @param page fuzzyOrgNo orgNo orgName orgGrade curPage pageSize
	 * @return PageDTO
	 */
	@Override
	public PageDTO<SysOrgDTO> qrySysOrgByPage(Map<String, Object> page) {
		log.info("----------[SysOrgService]qrySysOrgByPage----------------");
		PageDTO<SysOrgDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		int pageSize = PageUtil.transParam2Page(page, pageDTO);

		UserDTO authUser = sysUserResource.getAuthUserInfo();
		String authOrgNo = authUser.getOrgNo();
		page.put("authOrgNo", authOrgNo);

		// 获取数据总条数
		int totalRow = sysOrgMapper.qryTotalRowOrg(page);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		// 获取分页数据
		List<SysOrg> orgDO = sysOrgMapper.qryOrgByPage(page);
		List<SysOrgDTO> dtoList = SysOrgConverter.INSTANCE.domain2dto(orgDO);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setTotalPage(totalPage);
		pageDTO.setPageSize(pageSize);
		pageDTO.setRetList(dtoList);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	/**
	 * 查询所有机构类型
	 *
	 * @return ListDTO对象
	 */
	@Override
	public ListDTO<SysOrgGradeDTO> qrySysOrgGrade() {
		log.info("----------[SysOrgService]qrySysOrgGrade------------");
		ListDTO<SysOrgGradeDTO> dto = new ListDTO<>(RetCodeEnum.FAIL);
		List<SysOrgGrade> orgGradeDOList = sysOrgGradeMapper.selectAll();
		List<SysOrgGradeDTO> orgGradeDTOList = new ArrayList<>();
		// 循环遍历DO对象，将其转换为DTO对象
		for (SysOrgGrade orgGrade : orgGradeDOList) {
			SysOrgGradeDTO sysOrgGradeDTO = new SysOrgGradeDTO();
			sysOrgGradeDTO.setNo(orgGrade.getNo());
			sysOrgGradeDTO.setName(orgGrade.getName());
			sysOrgGradeDTO.setOrgType(orgGrade.getOrgType());
			orgGradeDTOList.add(sysOrgGradeDTO);
		}
		dto.setRetList(orgGradeDTOList);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	/**
	 * 增加机构，需要用户对该机构等级有权限
	 *
	 * @param sysOrgDTO 机构
	 * @return DTO
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, timeout = 36000, rollbackFor = Exception.class)
	public DTO addSysOrg(SysOrgDTO sysOrgDTO) {
		log.info("----------[SysOrgService]addSysOrg------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (checkPermissionByOrgGradeNo(sysOrgDTO.getOrgGradeNo())) {
			SysOrg sysOrg = SysOrgConverter.INSTANCE.dto2domain(sysOrgDTO);
			int i = sysOrgMapper.insert(sysOrg);
			if (i > 0) {
				dto.setResult(RetCodeEnum.SUCCEED);
				reSortOrg();
			}
		}
		return dto;
	}


	/**
	 * 修改机构，需要用户对该机构等级有权限
	 *
	 * @param sysOrgDTO 机构
	 * @return DTO
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, timeout = 36000, rollbackFor = Exception.class)
	public DTO modSysOrgById(SysOrgDTO sysOrgDTO) {
		log.info("----------[SysOrgService]modSysOrgById------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (checkPermissionByOrgNo(sysOrgDTO.getNo())) {
			//使用工具将DTO对象转换成DO对象
			SysOrg sysOrg = SysOrgConverter.INSTANCE.dto2domain(sysOrgDTO);
			int i = sysOrgMapper.updateByPrimaryKey(sysOrg);
			if (i != 0) {
				dto.setResult(RetCodeEnum.SUCCEED);
				reSortOrg();
			}
		}
		return dto;
	}

	/**
	 * 删除机构,需要用户对该机构有权限
	 *
	 * @param no 机构号
	 * @return DTO
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO delSysOrgById(String no) {
		log.info("----------[SysOrgService]delSysOrgById------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		// 判断登录用户是否有权限对该机构进行操作
		if (!checkPermissionByOrgNo(no)) {
			return dto;
		}
		// 判断该机构下是否有子机构
		List<SysOrg> childrenList = sysOrgMapper.selectChildren(no);
		if (!childrenList.isEmpty()) {
			return dto;
		}
		// 判断该机构下是否有相关用户
		int x1 = sysOrgMapper.selectUserByPrimaryKey(no);
		if (x1 != 0) {
			return dto;
		}
		// 判断是否为金库
		int i1 = clrCenterTableMapper.selectClrCenterByPrimaryKey(no);
		if (i1 != 0) {
			//如果是金库，则删除金库信息
			int i2 = clrCenterTableMapper.deleteClrCenterByPrimaryKey(no);
			if (i2 == 0) {
				return dto;
			}
		}
		int i = sysOrgMapper.deleteByPrimaryKey(no);
		// 删除机构
		if (i != 0) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}
		return dto;
	}


	/**
	 * 机构重排序，Left 和 Right 表示机构范围，总行机构包括了所有机构
	 */
	private void reSortOrg() {
		List<OrgNode> orgNodes = sysOrgMapper.qryOrgTreeList();
		TreeBuilder orgTree = new TreeBuilder(orgNodes);
		orgTree.buildTree2();
		List<OrgNode> allNodes = orgTree.getAllNodes();
		sysOrgMapper.updateLeftAndRight(allNodes);
	}

	/**
	 * 当前用户机构是否属于目标机构，及其上级机构
	 *
	 * @param orgNo 目标机构编号
	 * @return boolean
	 */
	private boolean checkPermissionByOrgNo(String orgNo) {
		UserDTO authUser = sysUserResource.getAuthUserInfo();
		return sysOrgMapper.checkPermissionByOrgNo(authUser.getOrgNo(), orgNo) > 0;
	}

	/**
	 * 判断	`目标机构等级` 是否大于等于 `当前用户机构等级`
	 *
	 * @param orgGradeNo 目标机构等级
	 * @return boolean
	 */
	private boolean checkPermissionByOrgGradeNo(int orgGradeNo) {
		UserDTO authUser = sysUserResource.getAuthUserInfo();
		int authOrgGradeNo = sysOrgMapper.getOrgGradeNo(authUser.getOrgNo());
		return orgGradeNo >= authOrgGradeNo;
	}

	/**
	 * @Description 查询网点营业时间
	 * @Param [orgNo]
	 */
	@Override
	public ListDTO<OrgBusinessTimeDTO> qryOrgBusinessTime(String orgNo) {
		log.info("----------[SysOrgService]qryOrgBusinessTime----------------");
		ListDTO<OrgBusinessTimeDTO> dto = new ListDTO<>(RetCodeEnum.FAIL);
		List<OrgbusinessTimeDO> orgBusinessTimeList = orgBusinessTimeMapper.qryOrgBusinessTime(orgNo);
		List<OrgBusinessTimeDTO> orgBusinessTimeDTOS = OrgBusinessTimeConverter.INSTANCE.domain2dto(orgBusinessTimeList);
		dto.setRetList(orgBusinessTimeDTOS);
		dto.setResult(RetCodeEnum.SUCCEED);
		return dto;
	}

	/**
	 * @Description 修改网点营业时间
	 * @Param
	 */
	@Override
	public DTO modOrgBusinessTime(OrgBusinessTimeDTO dto) {
		DTO dto1 = new DTO(RetCodeEnum.FAIL.getCode(), "调整失败");

		String orgNo = StringUtil.parseString(dto.getOrgNo());
		String businessTimeList = JSONUtil.createJsonString(dto.getBusinessTimeList());

		List<OrgbusinessTimeDO> orgBusinessTimeList = orgBusinessTimeMapper.qryOrgBusinessTime(orgNo);
		if (null != orgBusinessTimeList && orgBusinessTimeList.size() != 0) {
			int x = orgBusinessTimeMapper.delOrgBusinessTimeByorgNo(orgNo);
			if (x < 1) {
				dto1.setRetCode(RetCodeEnum.FAIL.getCode());
				dto1.setRetMsg("删除原有营业时间信息失败");
				return dto1;
			}
		}

		//添加新的设备加钞周期信息
		if (businessTimeList != null && !"".equals(businessTimeList)) {
			JSONArray jsonArray = JSONUtil.parseJSONArray(businessTimeList);
			for (Object o : jsonArray) {
				JSONObject jsonObject = (JSONObject) o;
				OrgbusinessTimeDO orgbusinessTimeDO = new OrgbusinessTimeDO();
				orgbusinessTimeDO.setOrgNo(orgNo);
				orgbusinessTimeDO.setOrgTimeInterval(StringUtil.parseString(jsonObject.get("orgTimeInterval")));
				orgbusinessTimeDO.setOpenTime(StringUtil.parseString(jsonObject.get("openTime")));
				orgbusinessTimeDO.setCloseTime(StringUtil.parseString(jsonObject.get("closeTime")));
				orgbusinessTimeDO.setOrgDay(StringUtil.objectToInt(jsonObject.get("orgDay")));

				orgBusinessTimeMapper.insert(orgbusinessTimeDO);
			}
		}

		dto1.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto1.setRetMsg("修改成功");
		return dto1;

	}

	/**
	 * 获取清分中心下所有网点
	 *
	 * @param clrCenterNo 清分中心编号
	 * @return retMap：SysOrgDTO
	 */
	@Override
	public Map<String, Object> getNetpointsByClrCenterNo(String clrCenterNo) {
		log.info("------------[getNetpointsByClrNo]AddnotesPlanService-------------");
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", "FF");
		retMap.put("retMsg", "查询失败！");
		try {
			Map<String, Object> aMap = clrCenterService.getNetpointsByClrNo(clrCenterNo);

			String jsonResultModel = JSONUtil.createJsonString(aMap.get("retList"));

			List<SysOrgDTO> retList = new ArrayList<SysOrgDTO>();
			if (!"".equals(jsonResultModel)) {
				JSONArray jsonArray = JSONUtil.parseJSONArray(jsonResultModel);
				for (int i = 0; i < jsonArray.size(); i++) {
					JSONObject jsonEntity = jsonArray.getJSONObject(i);
					SysOrgDTO orgListDTO = new SysOrgDTO();
					orgListDTO.setNo(jsonEntity.getString("no"));
					orgListDTO.setName(jsonEntity.getString("name"));
					orgListDTO.setX(jsonEntity.getString("x"));
					orgListDTO.setY(jsonEntity.getString("y"));
					retList.add(orgListDTO);
				}
			}

			retMap.put("netPointList", retList);
			retMap.put("retCode", StringUtil.parseString(aMap.get("retCode")));
			retMap.put("retMsg", StringUtil.parseString(aMap.get("retMsg")));
			return retMap;
		} catch (Exception e) {
			retMap.put("retCode", "FF");
			retMap.put("retMsg", "查询失败");
			log.error("[getNetpointsByClrNo]异常", e);
			return retMap;
		}
	}

	/**
	 * 组织网点信息s(+其下设备信息s)
	 *
	 * @param =[设备编号，网点编号,网点名称,网点地址,顺序号]
	 * @return [[网点编号, 网点名称, 网点地址, 经度, 纬度, 设备数, 设备编号字符串表示, 排序号, 设备数量]]
	 * Mode liuyuan 2019/08/16 去除同一网点判断，否则同一网点设备只保留第一条记录，不能理解设备号字符串标识什么意思
	 */
	private List<Map<String, Object>> formNetpointsWithDevs(List<Map<String, Object>> tempList, boolean withSortNo, boolean withDevCnt) {
		List<String> netpointNos = new ArrayList<String>(); // 网点编号列表,按sort_no和org_no排序
		HashMap<String, String> netpointNoNameMap = new HashMap<String, String>(); // 网点编号:网点名称
		HashMap<String, String> netpointNoAddressMap = new HashMap<String, String>(); // 网点编号:网点地址
		HashMap<String, Double> netpointNoXMap = new HashMap<String, Double>(); //网点编号：经度
		HashMap<String, Double> netpointNoYMap = new HashMap<String, Double>(); //网点编号：纬度
		HashMap<String, String> netpointNoPlanAddnotesAmtMap = new HashMap<String, String>();//网点编号：加钞金额
		HashMap<String, String> netpointDevNoMap = new HashMap<String, String>(); //网点编号：设备
		HashMap<String, String> netpointKeyEventDetailMap = new HashMap<String, String>(); //网点编号：

		HashMap<String, ArrayList<Map<String, Object>>> netpointNoDevNosMap = new HashMap<String, ArrayList<Map<String, Object>>>(); // 网点编号:设备信息
		HashMap<String, Integer> netpointNoSortNoMap = new HashMap<String, Integer>(); // 网点编号:排序编号

		List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
		String netpointNo;
		for (Map<String, Object> aMap : tempList) {
			String devNo = StringUtil.parseString(aMap.get("devNo"));
			String orgNo = StringUtil.parseString(aMap.get("orgNo"));
			String orgName = StringUtil.parseString(aMap.get("orgName"));
			String address = StringUtil.parseString(aMap.get("address"));
			String planAddnotesAmt = StringUtil.parseString(aMap.get("planAddnotesAmt"));
			Double x = StringUtil.ch2Double(aMap.get("x").toString());
			Double y = StringUtil.ch2Double(aMap.get("y").toString());

			int sortNo = StringUtil.objectToInt(aMap.get("sortNo"));
			String keyEventDetail = StringUtil.parseString(aMap.get("keyEventDetail"));

			netpointNo = orgNo;

			netpointNos.add(netpointNo);
			netpointNoNameMap.put(netpointNo, StringUtil.parseString(orgName));
			netpointNoAddressMap.put(netpointNo, StringUtil.parseString(address));
			netpointNoPlanAddnotesAmtMap.put(netpointNo, StringUtil.parseString(planAddnotesAmt));//加钞金额
			netpointDevNoMap.put(netpointNo, StringUtil.parseString(devNo));
			netpointKeyEventDetailMap.put(netpointNo, StringUtil.parseString(keyEventDetail));
			netpointNoXMap.put(netpointNo, x);
			netpointNoYMap.put(netpointNo, y);
			netpointNoSortNoMap.put(netpointNo, sortNo);

			//创建设备号列表
			ArrayList<Map<String, Object>> netpointNoDevNoList = new ArrayList<Map<String, Object>>();
			netpointNoDevNosMap.put(netpointNo, netpointNoDevNoList);

			Map<String, Object> entity = new HashMap<String, Object>();
			entity.put("orgNo", orgNo);
			entity.put("devNo", netpointDevNoMap.get(orgNo));
			entity.put("keyEventDetail", netpointKeyEventDetailMap.get(orgNo));
			entity.put("orgName", netpointNoNameMap.get(orgNo));
			entity.put("address", netpointNoAddressMap.get(orgNo));
			entity.put("planAddnotesAmt", netpointNoPlanAddnotesAmtMap.get(orgNo));
			entity.put("x", netpointNoXMap.get(orgNo));
			entity.put("y", netpointNoYMap.get(orgNo));
			if (withSortNo) {
				entity.put("sortNo", netpointNoSortNoMap.get(orgNo));
			}
			if (withDevCnt) {
				entity.put("devCnt", netpointNoDevNosMap.get(orgNo).size());
			}
			retList.add(entity);
		}
		return retList;
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
			List<Map<String, Object>> retList = sysOrgMapper.qryOrgDevsForMap();
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

	@Override
	public Map<String, Object> getNetpointsWithDevsOfGroup(String jsonString) {
		log.info("------------[getNetpointsWithDevsOfGroup]GroupService-------------");

		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("retCode", RetCodeEnum.FAIL.getCode());
		retMap.put("retMsg", RetCodeEnum.FAIL.getTip());
		try {
			JSONObject params = JSONUtil.parseJSONObject(jsonString);
			String addnotesPlanNo = StringUtil.parseString(params.get("addnotesPlanNo"));
			String groupNo = StringUtil.parseString(params.get("groupNo"));

			//查询计划设备数
			AddnotesPlanDTO addnotesPlan = addnotesPlanInnerResource.selectByPrimaryKey(addnotesPlanNo);
			if (addnotesPlan == null) {
				retMap.put("retMsg", "编号为" + addnotesPlanNo + "的加钞计划不存在");
				log.error("[getNetpointsWithDevsOfGroup]编号为" + addnotesPlanNo + "的加钞计划不存在");
				return retMap;
			}

			HashMap<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("addnotesPlanNo", addnotesPlanNo);
			paramMap.put("lineNo", groupNo);
			List<Map<String, Object>> netpointsOfGroupDev = sysOrgMapper.getNetPointsWithGroup(paramMap);
			List<Map<String, Object>> retList = this.formNetpointsWithDevs(netpointsOfGroupDev, true, true);

			retMap.put("retList", retList);
			retMap.put("retCode", RetCodeEnum.SUCCEED.getCode());
			retMap.put("retMsg", "分组下的网点信息查询成功!");
		} catch (Exception e) {
			retMap.put("retCode", RetCodeEnum.EXCEPTION.getCode());
			retMap.put("retMsg", "分组下的网点信息查询异常!");
			log.error("getNetpointsWithDevsOfGroup Fail: ", e);
		}
		return retMap;
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

			List<Map<String, Object>> list = sysOrgMapper.qryOrgListByCity(cityName);
			dto.setRetList(list);
			dto.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("[qryPointsByCityName] error" + e);
			dto.setResult(RetCodeEnum.FAIL);
		}
		return dto;
	}


	// -----------------------------------------------
	//                   内部服务，待优化
	// -----------------------------------------------


	@Override
	public List<String> qryOrgRegion(String clrOrgNo) {
		return sysOrgMapper.qryOrgRegion(clrOrgNo);
	}

	@Override
	public List<Map<String, Object>> getNetpointsByClrNo(String clrCenterNo) {
		return sysOrgMapper.getNetpointsByClrNo(clrCenterNo);
	}

	@Override
	public int qryOrgGradeNoByOrgNo(String orgNo) {
		return sysOrgMapper.qryOrgGradeNoByOrgNo(orgNo);
	}

	@Override
	public int qryClrCenterFlag(String orgNo) {
		return sysOrgMapper.qryClrCenterFlag(orgNo);
	}

	@Override
	public Map<String, String> qryCenterByNo(String no) {
		return sysOrgMapper.qryCenterByNo(no);
	}

	@Override
	public Map<String, String> qryDevInfoByNo(String devNo) {
		return sysOrgMapper.qryDevInfoByNo(devNo);
	}

	@Override
	public List<String> selectOrgNoList(String lineNo, Integer orgGradeNo) {
		return sysOrgMapper.selectOrgNoList(lineNo, orgGradeNo);
	}

	@Override
	public List<SysOrgDTO> qryNetworksByNetworkLineNo(String clrCenterNo, String networkLineNo) {
		List<SysOrg> list = sysOrgMapper.qryNetworksByNetworkLineNo(clrCenterNo, networkLineNo);
		return SysOrgConverter.INSTANCE.domain2dto(list);
	}

	@Override
	public String selectLineNo(String customerNo) {
		return sysOrgMapper.selectLineNo(customerNo);
	}
}
