package com.zjft.microservice.treasurybrain.datainsight.repository;

import com.zjft.microservice.treasurybrain.common.domain.SysOrg;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("sysOrgMapperGisBusiness")
public interface SysOrgMapper {
    /*int deleteByPrimaryKey(String no);

	int insert(SysOrg record);

	int insertSelective(SysOrg record);

	SysOrg selectByPrimaryKey(String no);

	int updateByPrimaryKeySelective(SysOrg record);

	int updateByPrimaryKey(SysOrg record);

	SysOrg selectDetailByPrimaryKey(String no);

	List<SysOrg> selectByName(String name);

	List<SysOrg> selectChildren(String id);

	List<SysOrg> qryAll();

	List<SysOrg> qryAllOrg(Map<String, Object> params);

	List<SysOrg> qryOrg(Map<String, Object> params);

    List<SysOrgDTO> qryOrgTreeList(Map<String, Object> params);

//	List<Object[]> qryOrgByFilter(@Param ("org") String org, @Param ("filter") String filter,@Param ("orgTypeId") String orgTypeId,@Param ("limit") String limit) ;

//    List<SysOrgDTO> selectOrg(@Param ("filter") String filter,@Param ("orgType") Integer orgType,@Param ("userOrgNo") String userOrgNo);

	List<SysOrg> qryBranchOrg();

	List<SysOrg> qryOrgByNo(String no);

	List<Map<String, Object>> getNetpointsByClrNo(String clrCenterNo);*/

	int qryTotalRowOrg(Map<String, Object> paramMap);

	List<Map<String, Object>> qryMessageForRegion();

	List<Map<String, Object>> qryMessageForCity();

	/**
	 * 根据城市名称查询网点
	 * @param cityName 城市名
	 * @return 网点列表
	 */
	List<SysOrg> qryOrgByCity(String cityName);

	/**
	 * 在地图中查询网点
	 * @return 网点列表
	 */
	List<Map<String, Object>> qryOrgDevsForMap();

	/**
	 * 根据城市名称查询网点
	 * @param cityName 城市名称
	 * @return 网点列表
	 */
	List<Map<String, Object>> qryOrgListByCityName(String cityName);

	/**
	 * 获取一级子机构
	 * @param orgNo
	 * @return
	 */
	List<SysOrg> getChildrenLevel1(String orgNo);

	/**
	 * 获取二级子机构
	 * @param orgNo
	 * @return
	 */
//	List<SysOrg> getChildrenLevel2(String orgNo);

	/**
	 * 查询每个机构的子机构
	 * @return
	 */
	List<Map<String, Object>> qryChildrenMap();

	/**
	 * 查询机构所在城市
	 * @param orgNo
	 * @return
	 */
	List<String> qryOrgRegion(String orgNo);
}
