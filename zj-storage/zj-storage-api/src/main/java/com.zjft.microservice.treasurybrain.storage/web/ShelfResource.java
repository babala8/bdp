package com.zjft.microservice.treasurybrain.storage.web;

/**
 * @author 常 健
 * @since 2020/1/2
 */
public interface ShelfResource {

	int updateStatusByNo(String shelfNo, int status);

	int qryExist(String shelfNo);

	int qryShelfStatus(String shelfNo);
}
