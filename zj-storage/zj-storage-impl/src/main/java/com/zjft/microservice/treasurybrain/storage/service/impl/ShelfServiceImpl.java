package com.zjft.microservice.treasurybrain.storage.service.impl;

import com.zjft.microservice.treasurybrain.storage.repository.StorageShelfTableMapper;
import com.zjft.microservice.treasurybrain.storage.service.ShelfService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 常 健
 * @since 2020/1/2
 */
@Service
public class ShelfServiceImpl implements ShelfService {

	@Resource
	private StorageShelfTableMapper storageShelfTableMapper;

	@Override
	public int updateStatusByNo(String shelfNo, int status) {
		return storageShelfTableMapper.modShelfStatus(shelfNo, status);
	}

	@Override
	public int qryExist(String shelfNo) {
		return storageShelfTableMapper.qryShelfNo(shelfNo);
	}

	@Override
	public int qryShelfStatus(String shelfNo) {
		return storageShelfTableMapper.qryShelfStatus(shelfNo);
	}
}
