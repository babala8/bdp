package com.zjft.microservice.treasurybrain.datainsight.service.impl;

import com.zjft.microservice.treasurybrain.common.util.ServletRequestUtil;
import com.zjft.microservice.treasurybrain.datainsight.repository.SaikuMapper;
import com.zjft.microservice.treasurybrain.datainsight.service.SaikuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 杨光
 * @since 2019-04-01
 */
@Service
@Slf4j
public class SaikuServiceImpl implements SaikuService {

	private final SaikuMapper saikuMapper;

	@Autowired
	public SaikuServiceImpl(SaikuMapper saikuMapper) {
		this.saikuMapper = saikuMapper;
	}

	@Override
	public String getSaikuTokenByNo(String no) {
		try {
			log.info("------------[getSaikuTokenById]userService-------------");
			//String username = ServletRequestUtil.getUsername();
			return saikuMapper.getSaikuTokenByNo(no);
		} catch (Exception e) {
			log.error("[UpdateUsrDefView] Fail:" + e);
			return null;
		}
	}
}
