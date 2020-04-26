package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.common.dto.ObjectDTO;
import com.zjft.microservice.treasurybrain.managecenter.dto.BaseEntityDTO;
import com.zjft.microservice.treasurybrain.managecenter.dto.BaseEntityInfoDTO;
import com.zjft.microservice.treasurybrain.managecenter.service.EntityBaseInfoService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.ExcelImportUtils;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/10
 */
@Slf4j
@RestController
public class EntityBaseInfoResourceImpl implements EntityBaseInfoResource {

	@Resource
	private EntityBaseInfoService entityBaseInfoService;

	/**
	 * 根据网点查询款箱号
	 *
	 * @return
	 */
	@Override
	public ObjectDTO qryContainerNoList(String customerNo) {
		ObjectDTO dto = new ObjectDTO(RetCodeEnum.FAIL.getCode(), "查询失败");
		try {
			List<String> containerNoList = entityBaseInfoService.qryContainerNoList(customerNo);
			dto.setElement(containerNoList);
			dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
			dto.setRetMsg(RetCodeEnum.SUCCEED.getTip());
		} catch (Exception e) {
			log.error("查询款箱失败", e);
			dto.setRetException("查询款箱失败");
		}
		return dto;
	}

	@Override
	public PageDTO<BaseEntityInfoDTO> queryEntityByPage(Map<String, Object> paramMap) {
		try {
			return entityBaseInfoService.queryEntityByPage(paramMap);
		} catch (Exception e) {
			log.error("查询款箱基础信息列表失败", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO addEntityInfo(BaseEntityDTO baseEntityDTO) {
		try {
			String entityNo = baseEntityDTO.getEntityNo();
			String goodsNo = baseEntityDTO.getGoodsNo();
			if (StringUtil.isNullorEmpty(entityNo) || StringUtil.isNullorEmpty(goodsNo)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}else if(entityBaseInfoService.queryByEntityNo(entityNo)>0){
				log.error("添加失败，该款箱编号已存在");
				return new DTO(RetCodeEnum.AUDIT_OBJECT_EXIST);
			}
			return entityBaseInfoService.addEntityInfo(baseEntityDTO);
		}catch (Exception e) {
			log.error("新增标签信息异常：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO modEntityInfo(BaseEntityDTO baseEntityDTO) {
		try {
			String entityNo = baseEntityDTO.getEntityNo();
			if (StringUtil.isNullorEmpty(entityNo)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}else if(entityBaseInfoService.queryByEntityNo(entityNo)<1){
				log.error("修改失败，该款箱编号不存在");
				return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
			}
			return entityBaseInfoService.modEntityInfo(baseEntityDTO);
		} catch (Exception e) {
			log.error("修改款箱基础信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO delEntityInfoByEntityNo(String entityNo) {
		try {
			if (StringUtil.isNullorEmpty(entityNo)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return entityBaseInfoService.delEntityInfoByEntityNo(entityNo);
		} catch (Exception e) {
			log.error("删除款箱基础信息异常", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO importEntity(MultipartFile file) {
		log.info("------------[importEntity]EntityBaseInfoResource-------------");
		try{
			if(file==null){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件不能为空");
			}

			//验证文件名是否合格
			String fileName=file.getOriginalFilename();
			if(!ExcelImportUtils.validateExcel(fileName)){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件必须是Excel格式");
			}

			//进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
			long size=file.getSize();
			if(StringUtil.isEmpty(fileName) || size==0){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件不能为空");
			}

			//批量导入
			return entityBaseInfoService.importEntity(fileName,file);

		} catch (Exception e){
			log.error("批量导入容器失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}
}
