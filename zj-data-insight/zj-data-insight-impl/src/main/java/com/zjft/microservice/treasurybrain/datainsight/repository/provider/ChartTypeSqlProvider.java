package com.zjft.microservice.treasurybrain.datainsight.repository.provider;

import com.zjft.microservice.treasurybrain.datainsight.domain.ChartSubject;
import com.zjft.microservice.treasurybrain.datainsight.domain.ChartType;
import org.apache.ibatis.jdbc.SQL;


/**
 * @author 杨光
 */
public class ChartTypeSqlProvider {

	public String insertSelective(ChartType record) {

		return new SQL() {{
			INSERT_INTO("chart_type");
			VALUES("TYPE_ID", "#{typeId}");
			VALUES("TYPE_NAME", "#{typeName}");
			if (record.getTypeDesc() != null) {
				VALUES("TYPE_DESC", "#{typeDesc}");
			}
		}}.toString();
	}

	public String updateByPrimaryKeySelective(ChartSubject record) {
		return new SQL() {{
			UPDATE("chart_type");
			if (record.getSubjectName() != null) {
				SET("TYPE_NAME = #{typeName,jdbcType=VARCHAR}");
			}
			if (record.getSubjectName() != null) {
				SET("TYPE_DESC =  #{typeDesc,jdbcType=VARCHAR}");
			}
			WHERE("TYPE_ID = #{typeId,jdbcType=VARCHAR}");
		}}.toString();
	}
}
