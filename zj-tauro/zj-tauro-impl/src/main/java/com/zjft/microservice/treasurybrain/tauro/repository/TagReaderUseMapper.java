package com.zjft.microservice.treasurybrain.tauro.repository;

import com.zjft.microservice.treasurybrain.tauro.domain.TagReaderUseInfoDO;
import com.zjft.microservice.treasurybrain.tauro.po.TagReaderInfoPO;
import com.zjft.microservice.treasurybrain.tauro.po.TagReaderUseInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TagReaderUseMapper {
	/**
	 * 分页查询手持机领用记录
	 *
	 * @param paramMap 手持机领用记录查询参数集合
	 */
	List<TagReaderUseInfoDO> queryTagReaderUseListByPage(Map<String, Object> paramMap);

	/**
	 * 查询符合查询条件的手持机领用记录
	 *
	 * @param paramMap 手持机领用记录查询参数集合
	 */
	int qryTotalRowTagReaderUse(Map<String, Object> paramMap);

	/**
	 * 增加手持机领用记录
	 *
	 * @param tagReaderUseInfoPO 手持机领用信息
	 */
	int insert(TagReaderUseInfoPO tagReaderUseInfoPO);

	/**
	 * 修改手持机领用记录
	 *
	 * @param tagReaderUseInfoPO 手持机领用信息
	 */
	int update(TagReaderUseInfoPO tagReaderUseInfoPO);

	/**
	 * 根据编号删除手持机领用记录
	 *
	 * @param tagReaderUseNo 编号
	 */
	int delTagReaderUseInfoByTagReaderUseNo(String tagReaderUseNo);

	/**
	 * 查询已有手持机领用记录编号中最大的顺序号
	 */
	String getMaxNo();

	/**
	 * 更新标签读写器状态
	 */
	int updateTagReaderStatus(TagReaderInfoPO tagReaderInfoPO);

	/**
	 * 查询手持机领用记录详情
	 *
	 * @param tagReaderUseNo 编号
	 */
	TagReaderUseInfoDO queryTagReaderUseDetail(@Param("tagReaderUseNo") String tagReaderUseNo);

	/**
	 * 手持机领用记录审核
	 *
	 * @param tagReaderUseInfoPO 手持机领用审核信息
	 */
	int audit(TagReaderUseInfoPO tagReaderUseInfoPO);

	/**
	 * 手持机归还登记
	 *
	 * @param tagReaderUseInfoPO 手持机归还信息
	 */
	int returnBack(TagReaderUseInfoPO tagReaderUseInfoPO);

	/**
	 * 查询用户userName
	 *
	 * @param name 用户姓名
	 */
	@Select("select USERNAME from SYS_USER where NAME like concat(concat('%',#{requestOpNo,jdbcType=NUMERIC}),'%')")
	List<String> qryUserName(String name);
}
