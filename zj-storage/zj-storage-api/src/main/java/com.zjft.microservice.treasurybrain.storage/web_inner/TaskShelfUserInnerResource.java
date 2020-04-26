package com.zjft.microservice.treasurybrain.storage.web_inner;

import java.util.Map;

/**
 * @author 常 健
 * @since 2020/1/7
 */
public interface TaskShelfUserInnerResource {

	int insertByMap(Map<String, Object> map);

	int deleteByTaskNo(String taskNo);
}
