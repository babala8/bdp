package com.zjft.microservice.treasurybrain.datainsight.repository;


import com.zjft.microservice.treasurybrain.datainsight.domain.ChartType;
import com.zjft.microservice.treasurybrain.datainsight.repository.provider.ChartTypeSqlProvider;
import org.apache.ibatis.annotations.*;

/**
 * @author 杨光
 */
@Mapper
public interface ChartTypeMapper {

	@Delete("delete from chart_type where TYPE_ID = #{typeId,jdbcType=VARCHAR}")
    int deleteByPrimaryKey(String typeId);


	@Insert("insert into chart_type (TYPE_ID, TYPE_NAME, TYPE_DESC) " +
			"values (#{typeId,jdbcType=VARCHAR}, #{typeName,jdbcType=VARCHAR}, #{typeDesc,jdbcType=VARCHAR})")
    int insert(ChartType record);


	@InsertProvider(type = ChartTypeSqlProvider.class,method = "insertSelective")
    int insertSelective(ChartType record);


	@Select("select TYPE_ID, TYPE_NAME, TYPE_DESC from chart_type where TYPE_ID = #{typeId,jdbcType=VARCHAR}")
    ChartType selectByPrimaryKey(String typeId);


	@InsertProvider(type = ChartTypeSqlProvider.class,method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ChartType record);


	@Update("update chart_type set TYPE_NAME = #{typeName,jdbcType=VARCHAR},TYPE_DESC = #{typeDesc,jdbcType=VARCHAR} " +
			"where TYPE_ID = #{typeId,jdbcType=VARCHAR}")
    int updateByPrimaryKey(ChartType record);
}