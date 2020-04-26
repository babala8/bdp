package com.zjft.microservice.treasurybrain.channelcenter.repository;

import com.zjft.microservice.treasurybrain.channelcenter.domain.OrgbusinessTimeDO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.OrgBusinessTimeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 崔耀中
 * @since 2019/11/19
 */
@Mapper
@Repository("orgBusinessTimeMapper")
public interface OrgBusinessTimeMapper {

	/**
	 * @Description 查询网点营业时间
	 * @Param
	 */
	List<OrgbusinessTimeDO> qryOrgBusinessTime(String orgNo);

	/**
	 * @Description 根据号删除网点营业时间
	 * @Param
	 */
	int delOrgBusinessTimeByorgNo(String orgNo);

	/**
	 * @Description 插入网点营业时间
	 * @Param
	 */
	int insert(OrgbusinessTimeDO orgbusinessTimeDO);




}
