package com.zjft.microservice.treasurybrain.managecenter.service;

import com.zjft.microservice.treasurybrain.managecenter.dto.TagReaderInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

import java.util.Map;

public interface TagReaderInfoService {

	DTO addTagReaderInfo(TagReaderInfoDTO tagReaderInfoDTO);

	PageDTO<TagReaderInfoDTO> queryTagReaderInfoByPage(Map<String, Object> paramsMap);

	DTO modTagReaderInfo(TagReaderInfoDTO tagReaderInfoDTO);

	DTO delTagReaderInfoByNo(String no);

	int queryTagReaderStatusByNo(String tagReaderNo);
}
