package com.zjft.microservice.treasurybrain.linecenter.repository;

import com.zjft.microservice.treasurybrain.linecenter.dto.HolidayLineRunDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HolidayLineRunMapper {

	int selectExistsFlag(@Param("theYearMonth")String theYearMonth, @Param("lineNo") String lineNo);

	int deleteLine(@Param("startLineRunNo")String startLineRunNo, @Param("endLineRunNo") String endLineRunNo);

	int insertLineRunNetDeatil(List<HolidayLineRunDTO> holidayLineRunDTOList);

	int updateNetCount(@Param("startLineRunNo")String startLineRunNo, @Param("endLineRunNo") String endLineRunNo);
}
