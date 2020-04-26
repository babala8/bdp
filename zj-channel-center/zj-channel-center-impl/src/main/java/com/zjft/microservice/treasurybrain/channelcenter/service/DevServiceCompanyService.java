package com.zjft.microservice.treasurybrain.channelcenter.service;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevServiceCompanyDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 杨光
 * @since 2019-04-02
 */
public interface DevServiceCompanyService {

	PageDTO<DevServiceCompanyDTO> queryDevCompanyList(String name,String type);


	DTO addDevCompany(DevServiceCompanyDTO dto);


	DTO modDevCompany(DevServiceCompanyDTO dto);


	DTO delDevCompanyByNo(String no);

	/**
	 * 批量导入服务商信息
	 * @param fileName
	 * @param mfile
	 * @return
	 */
	DTO importCompany(String fileName, MultipartFile mfile);

	String qryNoByName(String name);

}
