package com.zjft.microservice.treasurybrain.managecenter.service;

import com.zjft.microservice.treasurybrain.managecenter.dto.TagDTO;
import com.zjft.microservice.treasurybrain.managecenter.po.TagInfoPO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/6/11
 */
public interface TagService {
	/**
	 * 分页查询标签信息列表
	 * @param paramMap
	 */
	PageDTO<TagDTO> queryTagList(Map<String, Object> paramMap);

	/**
	 * 增加标签信息
	 * @param tagDTO
	 */
	DTO addTagInfo(TagDTO tagDTO);

	/**
	 * 修改标签信息
	 * @param tagDTO
	 */
	DTO modTagInfo(TagDTO tagDTO);

	/**
	 * 根据标签编号删除标签信息
	 * @param tagTid
	 */
	DTO delTagInfoByTagTid(String tagTid);

	/**
	 * 查询标签编号对应的标签状态
	 * @param tagTid
	 */
	Integer queryTagStatusByTagTid(String tagTid);

	/**
	 * 查询该标签编号是否已经存在
	 * @param tagTid
	 */
	Integer queryByTagTid(String tagTid);

	/**
	 * 根据标签编号更新标签状态
	 * @author liuyuan
	 * @param tagInfoPO 编号 状态
	 * @return  成功数量
	 */
	Integer updateStatusByTagTid(TagInfoPO tagInfoPO);

	/**
	 * 批量导入标签信息
	 * @param fileName
	 * @param mfile
	 * @return
	 */
	DTO importTag(String fileName, MultipartFile mfile);
}
