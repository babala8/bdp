package com.zjft.microservice.treasurybrain.datainsight.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.UserDTO;
import com.zjft.microservice.treasurybrain.common.util.CalendarUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.datainsight.domain.GisPointInfoDO;
import com.zjft.microservice.treasurybrain.datainsight.dto.GisPointInfoDTO;
import com.zjft.microservice.treasurybrain.datainsight.repository.GisPointInfoMapper;
import com.zjft.microservice.treasurybrain.datainsight.service.GisService;
import com.zjft.microservice.treasurybrain.usercenter.web.SysUserResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * @author 杨光
 * @author 常健
 */

@Slf4j
@Service
public class GisServiceImpl implements GisService {

	private final GisPointInfoMapper gisPointInfoMapper;
	private final SysUserResource sysUserResource;

	@Autowired
	public GisServiceImpl(GisPointInfoMapper gisPointInfoMapper, SysUserResource sysUserResource) {
		this.gisPointInfoMapper = gisPointInfoMapper;
		this.sysUserResource = sysUserResource;
	}

	@Override
	public void addGisPointInfo(Map<String, Object> params) {
		log.info("------------[addGisPointInfo]gisService-------------");
		//operator（对应表中的creator字段）通过登录账号获取，其他参数则通过前端接口输入
		try {
			UserDTO authUser = sysUserResource.getAuthUserInfo();

			String operator = authUser.getUsername();
			String orgNo = StringUtil.parseString(params.get("orgNo"));
			Integer status = StringUtil.objectToInt(params.get("status"));
			String jsTemplate = StringUtil.parseString(params.get("jsTemplate"));
			String htmlTemplate = StringUtil.parseString(params.get("htmlTemplate"));

			GisPointInfoDO gisPointInfoDO = new GisPointInfoDO();
			gisPointInfoDO.setPointId(UUID.randomUUID().toString().replaceAll("-", ""));
			gisPointInfoDO.setPointOrgno(orgNo);
			gisPointInfoDO.setStatus(status);
			gisPointInfoDO.setPointCreator(operator);
			gisPointInfoDO.setLastestModOp(operator);
			gisPointInfoDO.setPointJs(jsTemplate);
			gisPointInfoDO.setPointHtml(htmlTemplate);
			gisPointInfoDO.setCreateDate(CalendarUtil.getSysTimeYMDHMS());
			gisPointInfoDO.setLastestModTime(CalendarUtil.getSysTimeYMDHMS());
			gisPointInfoMapper.createOrUpdateByOrgNo(gisPointInfoDO);
		} catch (Exception e) {
			log.error("addGisPointInfo Fail: ", e);
		}
	}

	@Override
	public GisPointInfoDTO qryGisPointInfo(String orgNo) {
		log.info("------------[qryGisPointInfo]gisService-------------");
		GisPointInfoDTO gisPointInfoDTO = new GisPointInfoDTO(RetCodeEnum.SUCCEED);
		try {
			GisPointInfoDO gisPointInfoDO = gisPointInfoMapper.selectByOrgNo(orgNo);
			if (null == gisPointInfoDO) {
				return new GisPointInfoDTO(RetCodeEnum.FAIL);
			}
			String htmlTemplate = StringUtil.parseString(gisPointInfoDO.getPointHtml());
			String jsTemplate = StringUtil.parseString(gisPointInfoDO.getPointJs());
			String operator = StringUtil.parseString(gisPointInfoDO.getLastestModOp());
			String status = StringUtil.parseString(gisPointInfoDO.getStatus());
			gisPointInfoDTO.setHtmlTemplate(htmlTemplate);
			gisPointInfoDTO.setJsTemplate(jsTemplate);
			gisPointInfoDTO.setOperator(operator);
			gisPointInfoDTO.setOrgNo(orgNo);
			gisPointInfoDTO.setStatus(status);
		} catch (Exception e) {
			log.error("qryGisPointInfo Fail: ", e);
			return new GisPointInfoDTO(RetCodeEnum.FAIL);
		}
		return gisPointInfoDTO;
	}
}
