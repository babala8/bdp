package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.managecenter.dto.TagReaderInfoDTO;
import com.zjft.microservice.treasurybrain.managecenter.service.TagReaderInfoService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@RestController
public class TagReaderInfoResourceImpl implements TagReaderInfoResource{

	@Resource
	private TagReaderInfoService tagReaderInfoService;

	@Override
	public DTO addTagReaderInfo(TagReaderInfoDTO tagReaderInfoDTO) {
		try {
			String readerType = tagReaderInfoDTO.getReaderType().toString();
			String status = tagReaderInfoDTO.getStatus().toString();
			///ClrCenterNo默认值010211
			if (StringUtil.isNullorEmpty(readerType) || StringUtil.isNullorEmpty(status)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return tagReaderInfoService.addTagReaderInfo(tagReaderInfoDTO);
		} catch (Exception e) {
			log.error("新增标签读写器失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public PageDTO<TagReaderInfoDTO> queryTagReaderInfoByPage(Map<String, Object> paramMap) {
		try {
			int curPage = StringUtil.objectToInt(paramMap.get("curPage"));
			int pageSize = StringUtil.objectToInt(paramMap.get("pageSize"));
			if (-1 == curPage) {
				curPage = 1;
			}
			if (-1 == pageSize) {
				pageSize = 20;
			}

			paramMap.put("curPage", curPage);
			paramMap.put("pageSize", pageSize);

			return tagReaderInfoService.queryTagReaderInfoByPage(paramMap);
		} catch (Exception e) {
			log.error("分页查询标签读写器失败", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO modTagReaderInfo(TagReaderInfoDTO tagReaderInfoDTO) {
		try {
			String tagReaderNo = tagReaderInfoDTO.getTagReaderNo();
			if (StringUtil.isNullorEmpty(tagReaderNo)) {
				return new DTO(RetCodeEnum.PARAM_LACK);
			}
			return tagReaderInfoService.modTagReaderInfo(tagReaderInfoDTO);
		} catch (Exception e) {
			log.error("修改标签读写器失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO delTagReaderInfoByNo(String no) {
		try {
			if (StringUtil.isNullorEmpty(no)) {
				return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
			}
			return tagReaderInfoService.delTagReaderInfoByNo(no);
		} catch (Exception e) {
			log.error("删除标签读写器失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}
}
