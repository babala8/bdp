package com.zjft.microservice.treasurybrain.common.util;

import com.zjft.microservice.treasurybrain.common.dto.PageDTO;

import java.util.Map;

/**
 * 分页查询参数工具类
 *
 * @author 韩通
 * @since 2019-06-13
 */
public class PageUtil {

	/**
	 * 分页查询参数模板
	 *
	 * @param paramMap 分页参数
	 * @param pageDTO  pageDTO
	 * @return pageSize 页面记录条数
	 */
	public static int transParam2Page(Map<String, Object> paramMap, PageDTO pageDTO) {
		int curPage = StringUtil.objectToInt(paramMap.get("curPage"));
		int pageSize = StringUtil.objectToInt(paramMap.get("pageSize"));

		if (-1 == curPage) {
			curPage = 1;
		}
		if (-1 == pageSize) {
			pageSize = 20;
		}
		int startRow = pageSize * (curPage - 1);
		int endRow = pageSize * curPage;
		paramMap.put("curPage", curPage);
		paramMap.put("pageSize", pageSize);
		paramMap.put("startRow", startRow);
		paramMap.put("endRow", endRow);

		pageDTO.setCurPage(curPage);
		pageDTO.setPageSize(pageSize);
		return pageSize;
	}

	/**
	 * 计算总页数
	 *
	 * @param pageSize 页面记录条数
	 * @param totalRow 总记录条数
	 * @return totalPage 总页数
	 */
	public static int computeTotalPage(int pageSize, int totalRow) {
		return totalRow % pageSize == 0 ? totalRow / pageSize : totalRow / pageSize + 1;
	}
}
