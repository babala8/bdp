package com.zjft.microservice.treasurybrain.channelcenter.repository;

import com.zjft.microservice.treasurybrain.channelcenter.util.OrgNode;
import com.zjft.microservice.treasurybrain.common.domain.AddnotesPlan;
import com.zjft.microservice.treasurybrain.common.domain.SysOrg;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository("sysOrgMapperChannel")
public interface SysOrgMapper {
	/**
	 * 删除机构
	 *
	 * @param no 机构号
	 * @return int
	 */
	int deleteByPrimaryKey(String no);

	/**
	 * 判断登录机构编号为authOrgNo的用户是否有权限对机构编号为no的机构进行操作
	 *
	 * @param authOrgNo 用户机构号
	 * @param no        待操作的机构号
	 * @return int
	 */
	int checkPermissionByOrgNo(@Param("authOrgNo") String authOrgNo, @Param("no") String no);

	/**
	 * 根据机构号获取机构等级
	 *
	 * @param orgNo 机构编号
	 * @return int
	 */
	int getOrgGradeNo(String orgNo);

	/**
	 * 添加机构信息
	 *
	 * @param record 机构信息
	 * @return int
	 */
	int insert(SysOrg record);

	int insertSelective(SysOrg record);

	/**
	 * 根据主键查询记录
	 *
	 * @param no 机构号
	 * @return SysOrg类型的对象
	 */
	SysOrg selectByPrimaryKey(String no);

	/**
	 * 查询机构详情
	 *
	 * @param sysOrg 机构号
	 * @return SysOrg类型的对象
	 */
	SysOrg selectDetailByPrimaryKey(String sysOrg);

	int updateByPrimaryKeySelective(SysOrg record);

	/**
	 * 修改机构
	 *
	 * @param record 机构
	 * @return int
	 */
	int updateByPrimaryKey(SysOrg record);

	/**
	 * 查询机构的子机构
	 *
	 * @param id 机构号
	 * @return List<SysOrg>
	 */
	List<SysOrg> selectChildren(String id);

	/**
	 * 判断机构号是否与用户关联
	 *
	 * @param orgNo 机构号
	 * @return int
	 */
	int selectUserByPrimaryKey(String orgNo);


	/**
	 * 查询SYS_ORG表中的所有数据
	 *
	 * @return List
	 */
	List<SysOrg> qryAll();

	/**
	 * 筛选出满足条件的所有记录
	 *
	 * @param params Map
	 * @return List
	 */
	List<SysOrg> qryAllOrg(Map<String, Object> params);

	List<SysOrg> qryOrgByPage(Map<String, Object> map);

	List<SysOrg> qryOrgFuzzyByAuth(@Param("authOrgNo") String authOrgNo, @Param("orgName") String orgName);

	/**
	 * 查询所有机构，用来重排序机构树
	 *
	 * @return List<OrgNode>
	 */
	@Select({"select  NO , PARENT_ORG  from SYS_ORG o"})
	@Results({
			@Result(column = "NO", property = "no", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "PARENT_ORG", property = "parentOrg", jdbcType = JdbcType.VARCHAR),
	})
	List<OrgNode> qryOrgTreeList();

	@Insert("<script> " +
			" <foreach collection='orgNodeList' item='item' index='index' open='begin' close=';end;' separator=';'> " +
			"  update SYS_ORG " +
			"  <set> " +
			"   LEFT = #{item.left,jdbcType=NUMERIC } ," +
			"   RIGHT = #{item.right,jdbcType=NUMERIC } " +
			"  </set>" +
			"  where NO = #{item.no,jdbcType=VARCHAR} \n" +
			" </foreach>" +
			"</script> "
	)
	int updateLeftAndRight(@Param("orgNodeList") List<OrgNode> orgNodeList);

//	List<Object[]> qryOrgByFilter(@Param ("org") String org, @Param ("filter") String filter,@Param ("orgTypeId") String orgTypeId,@Param ("limit") String limit) ;

//    List<SysOrgDTO> selectOrg(@Param ("filter") String filter,@Param ("orgType") Integer orgType,@Param ("userOrgNo") String userOrgNo);

	List<SysOrg> qryBranchOrg();

	List<Map<String, Object>> getNetpointsByClrNo(String clrCenterNo);

	/**
	 * 筛选出满足条件的记录条数
	 *
	 * @param paramMap Map
	 * @return int
	 */
	int qryTotalRowOrg(Map<String, Object> paramMap);

	List<Map<String, Object>> qryMessageForRegion();

	List<Map<String, Object>> qryMessageForCity();

	List<SysOrg> qryOrgByCity(String cityName);

	List<Map<String, Object>> qryOrgDevsForMap();

	List<Map<String, Object>> qryOrgListByCityName(String cityName);

	/**
	 * 获取下一级子机构
	 *
	 * @param id    当前登录用户的机构编号
	 * @param orgNo 父机构编号
	 * @return
	 */
	List<SysOrg> getChildrenLevel1(@Param("id") String id, @Param("parentOrgNo") String orgNo);


	/**
	 * 查询每个机构的子机构
	 *
	 * @return
	 */
	List<Map<String, Object>> qryChildrenMap();

	/**
	 * 查询机构所在城市
	 *
	 * @param orgNo
	 * @return
	 */
	List<String> qryOrgRegion(String orgNo);

	/**
	 * 查询机构所在城市
	 * @author zhangjs
	 * @param networkLineNo
	 * @return
	 */
	List<SysOrg> qryNetworksByNetworkLineNo(@Param("clrCenterNo") String clrCenterNo,@Param("networkLineNo") String networkLineNo);

	List<Map<String, Object>> getNetPointsWithGroup(Map<String, Object> paramMap);

	List<Map<String, Object>> qryOrgListByCity(String cityName);

	int checkPermissionByUsername(@Param("username") String username, @Param("authOrgNo") String authOrgNo);

	int qryOrgGradeNoByOrgNo(String orgNo);

	int qryClrCenterFlag(String orgNo);

	Map<String, String> qryCenterByNo(String no);

	Map<String, String> qryDevInfoByNo(String devNo);

	List<String> selectOrgNoList(@Param("lineNo")String lineNo,@Param("orgGradeNo")Integer orgGradeNo);

	String selectLineNo(String customerNo);

}
