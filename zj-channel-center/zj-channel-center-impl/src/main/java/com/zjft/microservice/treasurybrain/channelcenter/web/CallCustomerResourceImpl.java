package com.zjft.microservice.treasurybrain.channelcenter.web;

import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerDTO;
import com.zjft.microservice.treasurybrain.channelcenter.dto.CallCustomerTypeDTO;
import com.zjft.microservice.treasurybrain.channelcenter.service.CallCustomerService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.ListDTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zhangjs
 * @since 2019-09-21
 */
@Slf4j
@RestController
public class CallCustomerResourceImpl implements CallCustomerResource {

	@Resource
	private CallCustomerService callCustomerService;

	/**
	 * 查询上门客户列表
	 *
	 * @param page 分页参数
	 * @return PageDTO<DevTypeDTO>
	 */
	@Override
	public PageDTO<CallCustomerDTO> queryCallCustomerList(Map<String, Object> page) {
		PageDTO<CallCustomerDTO> pageDTO;
		try {
			int curPage = StringUtil.objectToInt(page.get("curPage"));
			int pageSize = StringUtil.objectToInt(page.get("pageSize"));

			if (-1 == curPage) {
				page.put("curPage", 1);
			}
			if (-1 == pageSize) {
				page.put("pageSize", 20);
			}
			pageDTO = callCustomerService.queryCallCustomerList(page);

		} catch (Exception e) {
			pageDTO = new PageDTO<>(RetCodeEnum.EXCEPTION);
			log.info("[queryCallCustomerList] :", e);
		}
		return pageDTO;
	}

	/**
	 * 新增上门客户<br/>
	 * callCustomerDTO 都不能为空
	 *
	 * @param callCustomerDTO 上门客户
	 * @return DTO
	 */
	@Override
	public DTO addCallCustomer(CallCustomerDTO callCustomerDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(callCustomerDTO.getCustomerShortName()) && !StringUtil.isNullorEmpty(callCustomerDTO.getCnCustomerLongName())) {
			try {
				dto = callCustomerService.addCallCustomer(callCustomerDTO);
			} catch (Exception e) {
				log.error("新增上门客户失败", e);
			}
		}
		return dto;
	}

	/**
	 * 修改上门客户
	 *
	 * @param callCustomerDTO 上门客户
	 * @return DTO
	 */
	@Override
	public DTO modCallCustomer(CallCustomerDTO callCustomerDTO) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(callCustomerDTO.getCustomerNo()) && !StringUtil.isNullorEmpty(callCustomerDTO.getCustomerShortName())) {
			try {
				dto = callCustomerService.modCallCustomer(callCustomerDTO);
			} catch (Exception e) {
				log.error("修改上门客户失败", e);
			}
		}
		return dto;
	}

	/**
	 * 删除上门客户
	 *
	 * @param no 上门客户编号
	 * @return DTO
	 */
	@Override
	public DTO delCallCustomerByNo(String no) {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		if (!StringUtil.isNullorEmpty(no)) {
			try {
				dto = callCustomerService.delCallCustomerByNo(no);
			} catch (Exception e) {
				log.error("删除上门客户失败", e);
			}
		}
		return dto;
	}

	@Override
	public PageDTO<CallCustomerTypeDTO> queryCallCustomerTypeListByPage(Map<String, Object> params) {
		return callCustomerService.queryCallCustomerTypeListByPage(params);
	}

	@Override
	public ListDTO<CallCustomerTypeDTO> queryCallCustomerTypeList(Map<String, Object> params) {
		return callCustomerService.queryCallCustomerTypeList(params);
	}
}
