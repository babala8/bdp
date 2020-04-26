package com.zjft.microservice.treasurybrain.tauro.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.tauro.dto.TagReaderUseDTO;

import java.util.Map;

public interface TagReaderUseService {

	/**
	 * 分页查询手持机领用记录
	 * @param paramMap 手持机领用记录查询参数集合
	 */
	PageDTO<TagReaderUseDTO> queryTagReaderUseListByPage(Map<String, Object> paramMap);

	/**
	 * 增加手持机领用记录
	 * @param tagReaderUseDTO 手持机领用信息
	 */
	DTO addTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO);

	/**
	 * 修改手持机领用记录
	 * @param tagReaderUseDTO 手持机领用信息
	 */
	DTO modTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO);

	/**
	 * 根据编号删除手持机领用记录
	 * @param tagReaderUseNo 编号
	 */
	DTO delTagReaderUseInfoByTagReaderUseNo(String tagReaderUseNo);

	/**
	 * 查询标签读写器编号对应的读写器状态
	 * @param tagReaderNo 标签读写器编号
	 */
	Integer queryTagReaderStatusByNo(String tagReaderNo);

	/**
	 * 查询手持机领用记录详情
	 * @param tagReaderUseNo 编号
	 */
	TagReaderUseDTO queryTagReaderUseDetail(String tagReaderUseNo);

	/**
	 * 手持机领用记录审核
	 * @param tagReaderUseDTO 手持机领用审核信息
	 * @return
	 */
	DTO auditTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO);

	/**
	 * 手持机归还登记
	 * @param tagReaderUseDTO 手持机归还信息
	 * @return
	 */
	DTO returnTagReaderUseInfo(TagReaderUseDTO tagReaderUseDTO);

}
