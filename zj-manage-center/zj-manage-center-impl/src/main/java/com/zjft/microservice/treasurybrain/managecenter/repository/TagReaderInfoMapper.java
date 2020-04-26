package com.zjft.microservice.treasurybrain.managecenter.repository;

import com.zjft.microservice.treasurybrain.managecenter.po.TagReaderInfoPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagReaderInfoMapper {
	/**
	 * 新增
	 */
	int insert(TagReaderInfoPO tagReaderInfoPO);

	/**
	 * 分页查询
	 */
	List<TagReaderInfoPO> queryTagReaderInfoByPage(Map<String, Object> params);

	/**
	 * 更新
	 */
	int updateByPrimaryKeySelective(TagReaderInfoPO tagReaderInfoPO);

	/**
	 * 根据标签读写器编号删除
	 */
	int deleteByPrimaryKey(String no);

	/**
	 * 查询符合条件的数据条数
	 */
	int qryTotalRow(Map<String, Object> params);

	/**
	 * 获取最大标签读写器编号
	 */
	String qryTagReaderNoMax(String clrCenterNo);

	/**
	 * 通过标签读写器编号查询状态
	 */
	Integer qryStatusByNo(String no);

	/**
	 * 通过标签读写器编号查询读写器是否存在
	 */
	Integer qryExistsByNo(String tagReaderNo);

}
