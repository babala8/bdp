package com.zjft.microservice.treasurybrain.usercenter.service;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.usercenter.dto.OutsourcingDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/21
 */
public interface OutsourcingService {
	/**
	 * 分页查询外包人员信息
	 *
	 * @param paramMap 外包人员信息查询参数
	 * @return 外包人员信息列表
	 */
	PageDTO<OutsourcingDTO> queryOutsourcingList(Map<String, Object> paramMap);

	/**
	 * 查询该编号信息是否存在
	 *
	 * @param no 人员编号
	 * @return 数据库中该人员编号对应的记录数
	 */
	Integer queryByNo(String no);

	/**
	 * 新增外包人员信息
	 *
	 * @param outsourcingDTO 外包人员基础信息
	 * @return 返回信息
	 */
	DTO addOutsourcingInfo(OutsourcingDTO outsourcingDTO);

	/**
	 * 修改外包人员信息
	 *
	 * @param no 人员编号
	 * @return 返回信息
	 */
	DTO modOutsourcingInfo(OutsourcingDTO no);

	/**
	 * 删除外包人员信息
	 *
	 * @param no 人员编号
	 * @return 返回信息
	 */
	DTO delOutsourcingInfoByNo(String no);

	/**
	 * 批量导入外包人员信息
	 *
	 * @param fileName 导入文件名
	 * @param file 上传的人员信息文件
	 * @return 返回信息
	 */
	DTO importOutsourcing(String fileName, MultipartFile file);

	/**
	 *
	 * 根据岗位查询外包人员
	 * @param post 岗位
	 * @return
	 */
	List<String> qryByPost(int post);
}
