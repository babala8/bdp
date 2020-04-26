package com.zjft.microservice.treasurybrain.datainsight.repository.provider;

import com.zjft.microservice.treasurybrain.datainsight.domain.ChartSubject;
import org.apache.ibatis.jdbc.SQL;


/**
 * @author 杨光
 */
public class ChartSubjectSqlProvider {

	public String insertSelective(ChartSubject record) {
		return new SQL() {{
			INSERT_INTO("chart_subject");
			VALUES("SUBJECT_ID", "#{subjectId}");
			VALUES("SUBJECT_NAME", "#{subjectName}");
			if (record.getSubjectDesc() != null) {
				VALUES("SUBJECT_DESC", "#{subjectDesc}");
			}
		}}.toString();
	}

	public String updateByPrimaryKeySelective(ChartSubject record) {
		return new SQL() {{
			UPDATE("chart_subject");
			if (record.getSubjectName() != null) {
				SET("SUBJECT_NAME = #{subjectName,jdbcType=VARCHAR}");
			}
			if (record.getSubjectName() != null) {
				SET("SUBJECT_DESC = #{subjectDesc,jdbcType=VARCHAR}");
			}
			WHERE("SUBJECT_ID = #{subjectId,jdbcType=INTEGER}");
		}}.toString();
	}
}
