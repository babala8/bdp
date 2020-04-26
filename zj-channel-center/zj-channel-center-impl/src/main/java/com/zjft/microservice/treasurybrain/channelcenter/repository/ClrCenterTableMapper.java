package com.zjft.microservice.treasurybrain.channelcenter.repository;

import com.zjft.microservice.treasurybrain.common.domain.ClrCenterTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ClrCenterTableMapper {
	/*int deleteByPrimaryKey(String clrCenterNo);

	int insert(ClrCenterTable record);

	int insertSelective(ClrCenterTable record);*/

	ClrCenterTable selectByPrimaryKey(String clrCenterNo);

	int updateByPrimaryKeySelective(ClrCenterTable record);

	//int updateByPrimaryKey(ClrCenterTable record);

	List<ClrCenterTable> getClrCenterListByOrgNo(String orgNo);

	List<Map<String, Object>> getClrCenterByClrNo(String clrCenterNo);

	List<String> getClrCenterOrgNo(String clrCenterNo);

	//List<ClrCenterTable> getAllClrCenterList();

	List<ClrCenterTable> qryClrCenterListByAuth(Map<String, Object> paramMap);

	List<String> clrCenterNoList();

	/**
	 * 查询机构名称
	 *
	 *
	 * @return
	 */
	String getOrgNameByClrNo(String paramMap);

	/**
	 * 根据金库编号判断其是否为自动化库
	 *
	 * @param clrCenterNo 金库编号
	 * @return int
	 */
	int qryClrCenterIsAuto(@Param("clrCenterNo") String clrCenterNo);

	/**
	 * 根据机构号是否与金库关联
	 *
	 * @param orgNo 机构号
	 * @return int
	 */
	int selectClrCenterByPrimaryKey(String orgNo);

	/**
	 * 根据机构号删除金库信息
	 *
	 * @param orgNo 机构号
	 * @return int
	 */
	int deleteClrCenterByPrimaryKey(String orgNo);
}
