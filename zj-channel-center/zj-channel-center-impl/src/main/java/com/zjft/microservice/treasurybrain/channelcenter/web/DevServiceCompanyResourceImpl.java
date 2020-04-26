package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.DevServiceCompanyDTO;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevServiceCompanyService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.ExcelImportUtils;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author 常健
 * @since 2019-04-04
 */
@Slf4j
@RestController
public class DevServiceCompanyResourceImpl implements DevServiceCompanyResource {
	@Resource
	private DevServiceCompanyService devServiceCompanyService;

	/**
	 * 暂时只实现模糊查询，分页后面需求需要再修改
	 */
	@Override
	public PageDTO<DevServiceCompanyDTO> queryDevCompanyList(String name,String type) {
		log.info("------------[queryDevCompanyList]DevServiceCompanyResource-------------");
		PageDTO<DevServiceCompanyDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		try {
			pageDTO = devServiceCompanyService.queryDevCompanyList(name,type);
		} catch (Exception e) {
			log.error("查询设备维护商列表失败", e);
			pageDTO.setResult(RetCodeEnum.EXCEPTION);
		}
		return pageDTO;
	}

	@Override
	public DTO addDevCompany(DevServiceCompanyDTO devServiceCompanyDTO) {
		log.info("------------[addDevCompany]DevServiceCompanyResource-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			String name = devServiceCompanyDTO.getName();
			if (StringUtil.isNullorEmpty(name)) {
				return dto;
			}
			dto = devServiceCompanyService.addDevCompany(devServiceCompanyDTO);
		} catch (Exception e) {
			log.error("新增设备维护商失败", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public DTO modDevCompany(DevServiceCompanyDTO devServiceCompanyDTO) {
		log.info("------------[modDevCompany]DevServiceCompanyResource-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			String no = devServiceCompanyDTO.getNo();
			String name = devServiceCompanyDTO.getName();
			if (StringUtil.isNullorEmpty(no) || StringUtil.isNullorEmpty(name)) {
				return dto;
			}
			dto = devServiceCompanyService.modDevCompany(devServiceCompanyDTO);
		} catch (Exception e) {
			log.error("修改设备维护商失败", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public DTO delDevCompanyByNo(String no) {
		log.info("------------[delDevCompanyByNo]DevServiceCompanyResource-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			if (StringUtil.isNullorEmpty(no)) {
				return dto;
			}
			dto = devServiceCompanyService.delDevCompanyByNo(no);
		} catch (Exception e) {
			log.error("删除设备维护商失败", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public DTO importCompany(MultipartFile file) {
		log.info("------------[importCompany]DevServiceCompanyResource-------------");
		try{
			if(file==null){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件不能为空");
			}

			//验证文件名是否合格
			String fileName=file.getOriginalFilename();
			if(!ExcelImportUtils.validateExcel(fileName)){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件必须是excel格式");
			}

			//进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
			long size=file.getSize();
			if(StringUtil.isEmpty(fileName) || size==0){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件不能为空");
			}

			//批量导入
			return devServiceCompanyService.importCompany(fileName,file);

		} catch (Exception e){
			log.error("批量导入服务商失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}
}
