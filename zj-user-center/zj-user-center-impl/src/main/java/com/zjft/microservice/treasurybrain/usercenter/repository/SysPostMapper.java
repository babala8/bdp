package com.zjft.microservice.treasurybrain.usercenter.repository;

import com.zjft.microservice.treasurybrain.usercenter.po.SysPostPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liuyuan
 * @author 常健
 * @since 2019/9/21 10:29
 */
@Mapper
@Repository
public interface SysPostMapper {

	int qryTotalRow(Map<String, Object> paramMap);

	List<SysPostPO> qryByPage(Map<String, Object> paramMap);

	String maxNo();

	int add(SysPostPO sysPostPO);

	int mod(SysPostPO sysPostPO);

	int del(String postNo);

	List<SysPostPO> qryAll();

	@Select("select count(1) from SYS_USER_POST where POST_NO= #{postNo,jdbcType=VARCHAR}")
	int qryUserNumByPostNo(String postNo);

	SysPostPO select(String postNo);

	@Select("select count(1) from sys_post where post_No=#{postNo,jdbcType=VARCHAR}")
	int qryCount(String postNo);
}
