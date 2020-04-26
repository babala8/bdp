package com.zjft.microservice.treasurybrain.managecenter.web_inner;

import com.zjft.microservice.treasurybrain.managecenter.service.TagReaderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 崔耀中
 * @since 2020-01-06
 */
@Slf4j
@RestController
public class TagReaderInfoInnerResourceImpl implements TagReaderInfoInnerResource{

	@Resource
	private TagReaderInfoService tagReaderInfoService;

	@Override
	public int queryTagReaderStatusByNo(String tagReaderNo){
		return tagReaderInfoService.queryTagReaderStatusByNo(tagReaderNo);
	}

}
