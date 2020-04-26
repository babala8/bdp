package com.zjft.microservice.treasurybrain.usercenter.web;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.ExcelImportUtils;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.dto.OutsourcingDTO;
import com.zjft.microservice.treasurybrain.usercenter.service.OutsourcingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/21
 */
@Slf4j
@RestController
public class OutsourcingResourceImpl implements OutsourcingResource{

	@Resource
	private OutsourcingService outsourcingService;

	@Override
	public PageDTO<OutsourcingDTO> queryOutsourcingList(Map<String, Object> paramMap) {
		log.info("------------OutsourcingResourceImpl[queryOutsourcingList]-------------");
		try {
			return outsourcingService.queryOutsourcingList(paramMap);
		} catch (Exception e) {
			log.error("查询外包人员列表失败", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO addOutsourcingInfo(OutsourcingDTO outsourcingDTO) {
		log.info("------------OutsourcingResourceImpl[addOutsourcingInfo]-------------");
		try {
			String no = outsourcingDTO.getNo();
			if (StringUtil.isNullorEmpty(no) || StringUtil.isNullorEmpty(outsourcingDTO.getName())) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}else if(outsourcingService.queryByNo(no)>0){
				log.error("添加失败，该人员编号已存在");
				return new DTO(RetCodeEnum.AUDIT_OBJECT_EXIST);
			}
			return outsourcingService.addOutsourcingInfo(outsourcingDTO);
		}catch (Exception e) {
			log.error("新增外包人员信息异常：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO modOutsourcingInfo(OutsourcingDTO outsourcingDTO) {
		log.info("------------OutsourcingResourceImpl[modOutsourcingInfo]-------------");
		try {
			if (StringUtil.isNullorEmpty(outsourcingDTO.getNo())) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}else if(outsourcingService.queryByNo(outsourcingDTO.getNo())<1){
				log.error("修改失败，该人员编号不存在");
				return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
			}
			return outsourcingService.modOutsourcingInfo(outsourcingDTO);
		} catch (Exception e) {
			log.error("修改外包人员信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO delOutsourcingInfoByNo(String no) {
		log.info("------------OutsourcingResourceImpl[delOutsourcingInfoByNo]-------------");
		try {
			if (StringUtil.isNullorEmpty(no)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return outsourcingService.delOutsourcingInfoByNo(no);
		} catch (Exception e) {
			log.error("删除外包人员信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO importOutsourcing(MultipartFile file) {
		log.info("------------[importTag]TagResource-------------");
		try{
			String fileName=file.getOriginalFilename();
			if(StringUtil.isBlank(fileName)){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件不能为空");
			}

			//验证文件名是否合格
			if(!ExcelImportUtils.validateExcel(fileName)){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件必须是excel格式");
			}

			//进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
			long size=file.getSize();
			if(StringUtil.isEmpty(fileName) || size==0){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件不能为空");
			}

			//批量导入
			return outsourcingService.importOutsourcing(fileName,file);

		} catch (Exception e){
			log.error("批量导入外包人员信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public List<String> qryByPost(int post){
		return outsourcingService.qryByPost(post);
	}
}
