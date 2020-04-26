package com.zjft.microservice.treasurybrain.channelcenter.repository;

import com.zjft.microservice.treasurybrain.common.domain.SysOrgGrade;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 张笑
 */
@Mapper
@Repository("sysOrgGradeMapperExtend")
public interface SysOrgGradeMapper {

	/**
	 * 查询所有机构的类型
	 * @return List
	 */
	List<SysOrgGrade> selectAll();

	Map<String,Object> selectByPrimaryKey(int no);

}
