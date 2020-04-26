package com.zjft.microservice.treasurybrain.storage.web;

import com.zjft.microservice.treasurybrain.storage.service.ShelfService;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 常 健
 * @since 2020/1/2
 */
@RestController
public class ShelfResourceImpl implements ShelfResource {

	@Resource
	private ShelfService shelfService;


	@Override
	public int updateStatusByNo(String shelfNo, int status) {
		return shelfService.updateStatusByNo(shelfNo, status);
	}

	@Override
	public int qryExist(String shelfNo) {
		return shelfService.qryExist(shelfNo);
	}

	@Override
	public int qryShelfStatus(String shelfNo) {
		return shelfService.qryShelfStatus(shelfNo);
	}
}
