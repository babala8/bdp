package com.zjft.microservice.treasurybrain.usercenter.web_inner;

import com.zjft.microservice.treasurybrain.usercenter.dto.OutSourcingLineMapInnerDTO;

import java.util.List;

/**
 * @author 常 健
 * @since 2019/12/31
 */
public interface OutSourcingInnerResource {


	List<OutSourcingLineMapInnerDTO> qryInfoByDutyDate(String dutyDate);

	List<String> qryOpNameByLineRunNo(String lineRunNo);
}
