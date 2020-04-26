package com.zjft.microservice.treasurybrain.datainsight.repository;

import com.zjft.microservice.treasurybrain.datainsight.domain.SelfDefCharts;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SelfDefChartsMapper {

	@Delete(" delete from selfdef_charts where CHART_ID = #{chartId,jdbcType=VARCHAR}")
	int deleteByPrimaryKey(String chartId);


	@Insert("insert into selfdef_charts (CHART_ID, CHART_NAME, CHART_SUBJECT, CHART_TYPE, CHART_ICON,CHART_DESC, CHART_STATUS, " +
			"      CREATOR, CREATE_TIME, LASTEST_MOD_OP, LASTEST_MOD_TIME, CHART_DEF, CREATOR_ORGNO, CHART_OPTION) " +
			"values (#{chartId,jdbcType=VARCHAR}, #{chartName,jdbcType=VARCHAR}, #{chartSubject,jdbcType=INTEGER},  " +
			"      #{chartType,jdbcType=VARCHAR},#{chartIcon,jdbcType=VARCHAR}, #{chartDesc,jdbcType=VARCHAR},#{chartStatus,jdbcType=INTEGER},  " +
			"      #{creator,jdbcType=VARCHAR}, #{createTime,jdbcType=VARCHAR}, #{lastestModOp,jdbcType=VARCHAR},  " +
			"      #{lastestModTime,jdbcType=VARCHAR}, #{chartDef,jdbcType=LONGVARCHAR},  #{creatorOrgno,jdbcType=VARCHAR}, " +
			"      #{chartOption,jdbcType=LONGVARCHAR})")
	int insert(SelfDefCharts record);

//	@InsertProvider(type = SelfDefChartsSqlProvider.class, method = "insertSelective")
//	int insertSelective(SelfDefCharts record);


	@Select("select " +
			"    CHART_ID, CHART_NAME, CHART_SUBJECT, CHART_TYPE, CHART_ICON, CHART_DESC, CHART_STATUS, CREATOR, " +
			"    CREATE_TIME, LASTEST_MOD_OP, LASTEST_MOD_TIME ,CREATOR_ORGNO, CHART_DEF,CHART_OPTION " +
			"    from selfdef_charts " +
			"    where CHART_ID = #{chartId,jdbcType=VARCHAR}")
	@Results({
			@Result(column = "CHART_ID", property = "chartId", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "CHART_NAME", property = "chartName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_SUBJECT", property = "chartSubject", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CHART_TYPE", property = "chartType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_ICON", property = "chartIcon", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_DESC", property = "chartDesc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_STATUS", property = "chartStatus", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CREATOR", property = "creator", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CREATE_TIME", property = "createTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LASTEST_MOD_OP", property = "lastestModOp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LASTEST_MOD_TIME", property = "lastestModTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CREATOR_ORGNO", property = "creatorOrgno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_DEF", property = "chartDef", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_OPTION", property = "chartOption", jdbcType = JdbcType.VARCHAR)
	})
	SelfDefCharts selectByPrimaryKey(String chartId);


	@Update("update selfdef_charts " +
			"    set CHART_NAME = #{chartName,jdbcType=VARCHAR}, " +
			"      CHART_SUBJECT = #{chartSubject,jdbcType=INTEGER}, " +
			"      CHART_TYPE = #{chartType,jdbcType=VARCHAR}, " +
			"      CHART_ICON = #{chartIcon,jdbcType=VARCHAR}, " +
			"      CHART_DESC = #{chartDesc,jdbcType=VARCHAR}, " +
			"      CHART_STATUS = #{chartStatus,jdbcType=INTEGER}, " +
			"      LASTEST_MOD_OP = #{lastestModOp,jdbcType=VARCHAR}, " +
			"      LASTEST_MOD_TIME = #{lastestModTime,jdbcType=VARCHAR}, " +
			"      CHART_DEF = #{chartDef,jdbcType=LONGVARCHAR}, " +
			"      CHART_OPTION = #{chartOption,jdbcType=LONGVARCHAR} " +
			"    where CHART_ID = #{chartId,jdbcType=VARCHAR}")
	int updateByPrimaryKey(SelfDefCharts record);


	@Select("select CHART_ID , CHART_NAME, CHART_TYPE, CHART_ICON,CHART_DESC, CHART_OPTION " +
			"   from selfdef_charts s " +
			"   where 1=1  " +
			"   and s.CREATOR_ORGNO in " +
			"     (select o.no from SYS_ORG o left join SYS_ORG tOrg on tOrg.no= #{userOrgNo,jdbcType=VARCHAR} where o.left >= tOrg.LEFT and o.right <= tOrg.RIGHT)"+
			" order by CHART_TYPE ,CREATE_TIME DESC")
	@Results({
			@Result(column = "CHART_ID", property = "chartId", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "CHART_NAME", property = "chartName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_TYPE", property = "chartType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_ICON", property = "chartIcon", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_DESC", property = "chartDesc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_OPTION", property = "chartOption", jdbcType = JdbcType.VARCHAR)

	})
	List<SelfDefCharts> getSelfDefCharts(String userOrgNo);

	@Select("select  " +
			"   CHART_ID, CHART_NAME, CHART_SUBJECT, CHART_TYPE, CHART_ICON, CHART_DESC, CHART_STATUS, CREATOR," +
			"   CREATE_TIME, LASTEST_MOD_OP, LASTEST_MOD_TIME ,CREATOR_ORGNO ,CHART_DEF,CHART_OPTION " +
			"from selfdef_charts " +
			"where CHART_NAME = #{chartName,jdbcType=VARCHAR}")
	SelfDefCharts selectByChartName(String chartName);


	@Select("select a1.* from (select a2.*,rownum rn from " +
			"  (select  " +
			"    CHART_ID, CHART_NAME, CHART_SUBJECT, CHART_TYPE, CHART_ICON, CHART_DESC, CHART_STATUS, CREATOR," +
			"    CREATE_TIME, LASTEST_MOD_OP, LASTEST_MOD_TIME ,CREATOR_ORGNO ,CHART_DEF,CHART_OPTION " +
			"  from SELFDEF_CHARTS s " +
			"  where " +
			"      s.CREATOR_ORGNO in (select o.no from SYS_ORG o left join SYS_ORG tOrg on tOrg.no= #{userOrgNo,jdbcType=VARCHAR} where o.left >= tOrg.LEFT and o.right <= tOrg.RIGHT)"+
			"      AND s.CHART_SUBJECT ${chartSubject} " +
			"      AND s.CREATOR like #{chartCreator,jdbcType=VARCHAR}  " +
			"      AND s.CHART_NAME like #{chartName,jdbcType=VARCHAR}  " +
			"  order by s.CHART_ID  ) " +
			"a2) a1 " +
			"where rn > #{startRow,jdbcType=NUMERIC} and rn<=#{endRow,jdbcType=NUMERIC}")
	@Results({
			@Result(column = "CHART_ID", property = "chartId", jdbcType = JdbcType.VARCHAR, id = true),
			@Result(column = "CHART_NAME", property = "chartName", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_SUBJECT", property = "chartSubject", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CHART_TYPE", property = "chartType", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_ICON", property = "chartIcon", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_DESC", property = "chartDesc", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_STATUS", property = "chartStatus", jdbcType = JdbcType.NUMERIC),
			@Result(column = "CREATOR", property = "creator", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CREATE_TIME", property = "createTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LASTEST_MOD_OP", property = "lastestModOp", jdbcType = JdbcType.VARCHAR),
			@Result(column = "LASTEST_MOD_TIME", property = "lastestModTime", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CREATOR_ORGNO", property = "creatorOrgno", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_DEF", property = "chartDef", jdbcType = JdbcType.VARCHAR),
			@Result(column = "CHART_OPTION", property = "chartOption", jdbcType = JdbcType.VARCHAR)
	})
	List<SelfDefCharts> qryCharts(Map<String, Object> params);

//	List<String> queryChartTypes(Map<String, Object> params);

	@Select("select DISTINCT CREATOR " +
			" from SELFDEF_CHARTS s " +
			" where s.CREATOR_ORGNO in " +
			"    (select o.no from SYS_ORG o left join SYS_ORG tOrg on tOrg.no= #{userOrgNo,jdbcType=VARCHAR} where o.left >= tOrg.LEFT and o.right <= tOrg.RIGHT)"+
			" order by s.CREATOR ")
	List<String> queryChartCreators(String userOrgNo);


	@Select("select count(1)  from SELFDEF_CHARTS s  " +
			"where  " +
			"    s.CREATOR_ORGNO in (select o.no from SYS_ORG o left join SYS_ORG tOrg on tOrg.no= #{userOrgNo,jdbcType=VARCHAR} where o.left >= tOrg.LEFT and o.right <= tOrg.RIGHT)"+
			"    AND s.CHART_SUBJECT  ${chartSubject}  " +
			"    AND s.CREATOR like #{chartCreator,jdbcType=VARCHAR}  " +
			"    AND s.CHART_NAME like #{chartName,jdbcType=VARCHAR}  ")
	int qryTotalRowChart(Map<String, Object> paramMap);

//	int insertOrUpdate(SelfDefCharts selfDefCharts);
}