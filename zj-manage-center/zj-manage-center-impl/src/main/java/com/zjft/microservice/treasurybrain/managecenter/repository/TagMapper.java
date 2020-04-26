package com.zjft.microservice.treasurybrain.managecenter.repository;

import com.zjft.microservice.treasurybrain.managecenter.domain.TagInfoDO;
import com.zjft.microservice.treasurybrain.managecenter.po.TagInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/6/11
 */
@Mapper
public interface TagMapper {
	/**
	 * 分页查询标签信息列表
	 * @param paramMap
	 */
	List<TagInfoDO> queryTagListByTagTid(Map<String, Object> paramMap);

	/**
	 * 增加标签信息
	 * @param tagInfoPO
	 */
	int insert(TagInfoPO tagInfoPO);

	/**
	 * 增加标签信息
	 * @param tagInfoPO
	 */
	int insertOrUpdate(TagInfoPO tagInfoPO);

	/**
	 * 修改标签信息
	 * @param tagInfoPO
	 */
	int update(TagInfoPO tagInfoPO);

	/**
	 * 根据标签编号删除标签信息
	 * @param tagTid
	 */
	int delTagInfoByTagTid(String tagTid);

	/**
	 * 查询标签编号对应的标签状态
	 * @param tagTid
	 */
	int queryTagStatusByTagTid(String tagTid);

	/**
	 * 查询该标签编号是否已经存在
	 * @param tagTid
	 */
	int queryByTagTid(@Param("tagTid") String tagTid);

	/**
	 * 查询符合查询条件的标签总数
	 * @param paramMap
	 */
	int qryTotalRowTag(Map<String, Object> paramMap);

	/**
	 * 根据标签编号更新标签状态
	 * @author liuyuan
	 * @param tagInfoPO 编号 状态
	 * @return 成功数量
	 */
	int updateStatusByTagTid(TagInfoPO tagInfoPO);
}
