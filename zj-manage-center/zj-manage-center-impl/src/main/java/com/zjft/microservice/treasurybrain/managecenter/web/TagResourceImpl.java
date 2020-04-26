package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.managecenter.dto.TagDTO;
import com.zjft.microservice.treasurybrain.managecenter.service.TagService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.ExcelImportUtils;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StatusEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/6/11
 */
@Slf4j
@RestController
public class TagResourceImpl implements TagResource{
	@Resource
	private TagService tagService;

	@Override
	public PageDTO<TagDTO> queryTagList(Map<String, Object> paramMap) {
		log.info("------------[queryTagList]TagResource-------------");
		try {
			return tagService.queryTagList(paramMap);
		} catch (Exception e) {
			log.error("查询标签列表失败", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO addTagInfo(TagDTO tagDTO){
		log.info("------------[addTagInfo]TagResource-------------");
		try {
			String tagTid = tagDTO.getTagTid();
			if (StringUtil.isNullorEmpty(tagTid)) {
				return new DTO(RetCodeEnum.FAIL);
			}else if(tagService.queryByTagTid(tagTid)>0){
				log.error("添加失败，该标签编号已存在");
				return new DTO(RetCodeEnum.FAIL);
			}
			return tagService.addTagInfo(tagDTO);
		}catch (Exception e) {
			log.error("新增标签信息异常：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}

	}

	@Override
	public DTO modTagInfo(TagDTO tagDTO) {
		log.info("------------[modTagInfo]TagResource-------------");
		try {
			String tagTid = tagDTO.getTagTid();
			if (StringUtil.isNullorEmpty(tagTid)) {
				return new DTO(RetCodeEnum.FAIL);
			}
			return tagService.modTagInfo(tagDTO);
		} catch (Exception e) {
			log.error("修改标签信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO delTagInfoByTagTid(String tagTid) {
		log.info("------------[delTagInfoByTagTid]TagResource-------------");
		try {
			if (StringUtil.isNullorEmpty(tagTid)) {
				return new DTO(RetCodeEnum.FAIL);
			}else if(tagService.queryTagStatusByTagTid(tagTid) == StatusEnum.TagStatus.STATUS_STOPPED.getStatus()){
				return tagService.delTagInfoByTagTid(tagTid);
			}
			log.error("标签状态不是停用，无法删除");
			return new DTO(RetCodeEnum.FAIL);
		} catch (Exception e) {
			log.error("删除标签信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO importTag(MultipartFile file) {
		log.info("------------[importTag]TagResource-------------");
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
			return tagService.importTag(fileName,file);

		} catch (Exception e){
			log.error("批量导入标签失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}
}
