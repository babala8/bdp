package com.zjft.microservice.treasurybrain.managecenter.web_inner;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 葛瑞莲
 * @since 2020/01/11
 */
public interface TagReaderInfoInnerResource {

	int queryTagReaderStatusByNo(@RequestParam("tagReaderNo") String tagReaderNo);

}
