package com.zjft.microservice.treasurybrain.channelcenter.repository;

import com.zjft.microservice.treasurybrain.channelcenter.domain.DevClrTimeParamDO;
import com.zjft.microservice.treasurybrain.common.domain.DevBaseInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mapper
@Repository("devBaseInfoMapperExtend")
public interface DevBaseInfoMapper {
	
	List<DevBaseInfo> getAll();
	
    int deleteByPrimaryKey(String no);

    int insert(DevBaseInfo record);

    int insertSelective(DevBaseInfo record);

    DevBaseInfo selectByPrimaryKey(String no);
    
    DevBaseInfo selectDetailByPrimaryKey(String no);

    int updateByPrimaryKeySelective(DevBaseInfo record);

    int updateByPrimaryKey(DevBaseInfo record);


	List<Map<String, Object>> qryMessageForRegion();

	List<Map<String, Object>> qryMessageForCity();

	List<DevBaseInfo> selectDevsByOrgNo(String no);

	List<DevBaseInfo> selectDevByNoOrgNo(Map<String, Object> paramsMap);

	List<DevBaseInfo> selectDevListByParams(Map<String, Object> params);

	List<Map<String, Object>> qryDevTypesForView(String orgNo);

	List<Map<String, Object>> qryDevCatalogsForView(String orgNo);

	List<Map<String, Object>> qryDevNumForOrg(String orgNo);

	List<Map<String, Object>> qryDevNumGroupByOrg(Integer status);

	List<DevBaseInfo> getPostponeDevice(HashMap<String, Object> params);

	List<Map<String, Object>> getKeyEventDevice(Map<String, Object> params);

	List<Map<String, Object>> getKeyEventDeviceForfault(Map<String, Object> params);

	List<Map<String, Object>> getKeyEventDeviceForLineRun(Map<String, Object> params);

	List<Map<String, Object>> getAvaileAmtAndTimeInterval(Map<String, Object> params);

	DevBaseInfo qryDevByNoOrgNo(@Param("devNo") String devNo, @Param("orgNo") String orgNo);

	List<String> qryNearlyDevsByNo(@Param("devNo") String devNo, @Param("orgNo") String orgNo, @Param("clrCenterNo") String clrCenterNo);

	String qryFhOrgByDevNo(@Param("devNo") String devNo);

	Map<String, Object> qryDevBaseInfoByNo(@Param("devNo") String devNo);

	List<String> qryNoByStatus(@Param("devStatus") Integer devStatus);

	int qryTotalRow(Map<String, Object> paramsMap);

	List<Map<String, Object>> qryDevInfoByClrNo(@Param("clrCenterNo") String clrCenterNo);
//	List<DevBaseInfo> qryDevInfoByClrNo(@Param("clrCenterNo") String clrCenterNo);

	Integer getDevDayAvgAmt(@Param("devNo") String devNo, @Param("startDate") String startDate, @Param("endDate") String endDate);

	List<DevClrTimeParamDO> qryByDevNo(String devNo);



	@Select("select count(1) from dev_base_info " +
			"where " +
			"    ORG_NO in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{authOrgNo,jdbcType=VARCHAR} where o.left >= tOrg.LEFT and o.right <= tOrg.RIGHT) " +
			"    and no = #{devNo,jdbcType=VARCHAR} "
	)
	int checkPermissionByDevNo(@Param("devNo") String devNo, @Param("authOrgNo") String authOrgNo);


	@Select("select count(1) from sys_org " +
			"where " +
			"    no in (select o.no from SYS_ORG o left join SYS_ORG tOrg  on tOrg.no=#{authOrgNo,jdbcType=VARCHAR} where o.left >= tOrg.LEFT and o.right <= tOrg.RIGHT) " +
			"    and no = #{userOrgNo,jdbcType=VARCHAR}"
	)
	int checkPermissionByOrgNo(@Param("userOrgNo") String userOrgNo, @Param("authOrgNo") String authOrgNo);
}
