package com.zjft.microservice.treasurybrain.accountscenter.web_inner;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2020-01-07
 */
public interface BiztxlogInitInnerResource {

	List<Map<String, Object>> qryDevDayAvgAmt(String devNo, String tranDate);

	List<Map<String, Object>> getMaxDateOfDev(String devNo);

}
