package com.zjft.microservice.treasurybrain.managecenter.service;

import com.zjft.microservice.treasurybrain.managecenter.dto.BaseEntityDTO;
import com.zjft.microservice.treasurybrain.managecenter.dto.BaseEntityInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/11
 */
public interface EntityBaseInfoService {

	/**
	 * 根据网点查询款箱号
	 * @return 结果
	 */
	List<String> qryContainerNoList(String  customerNo);

	/**
	 * 分页查询款箱基础信息
	 *
	 * @param paramMap 查询参数
	 * @return 款箱基础信息列表
	 */
	PageDTO<BaseEntityInfoDTO> queryEntityByPage(Map<String, Object> paramMap);

	/**
	 * 查询该款箱编号是否已存在
	 *
	 * @param entityNo
	 * @return
	 */
	Integer queryByEntityNo(String entityNo);

	/**
	 * 添加款箱基础信息
	 *
	 * @param baseEntityDTO
	 * @return
	 */
	DTO addEntityInfo(BaseEntityDTO baseEntityDTO);

	/**
	 * 修改款箱基础信息
	 *
	 * @param baseEntityDTO
	 * @return
	 */
	DTO modEntityInfo(BaseEntityDTO baseEntityDTO);

	/**
	 * 删除款箱基础信息
	 *
	 * @param entityNo 物品编号
	 * @return
	 */
	DTO delEntityInfoByEntityNo(String entityNo);

	/**
	 * 批量导入容器信息
	 * @param fileName
	 * @param mfile
	 * @return
	 */
	DTO importEntity(String fileName, MultipartFile mfile);
}
