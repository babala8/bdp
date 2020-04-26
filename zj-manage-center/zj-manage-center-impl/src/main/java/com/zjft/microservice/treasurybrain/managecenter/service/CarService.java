package com.zjft.microservice.treasurybrain.managecenter.service;


import com.zjft.microservice.treasurybrain.managecenter.dto.CarDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2019/9/21
 */
public interface CarService {

	/**
	 * 分页查询车辆信息列表
	 * @param paramMap
	 */
	PageDTO<CarDTO> queryCarList(Map<String, Object> paramMap);

	/**
	 * 查询该车牌号是否已经存在
	 * @param carNumber
	 */
	Integer queryByCarNumber(String carNumber);

	/**
	 * 添加车辆信息
	 * @param carDTO
	 */
	DTO addCarInfo(CarDTO carDTO);

	/**
	 * 修改车辆信息
	 * @param carDTO
	 */
	DTO modCarInfo(CarDTO carDTO);

	/**
	 * 根据车辆编号删除车辆信息
	 * @param carNo
	 */
	DTO delCarInfoByNo(String carNo);

	/**
	 * 批量导入车辆信息
	 * @param fileName
	 * @param mfile
	 * @return
	 */
	DTO importCar(String fileName, MultipartFile mfile);

	List<CarDTO> qryAllCar();

	/**
	 * @Description 根据车辆编号查车牌号
	 * @Param carNo
	 */
	String qryCarNumberByNo(Integer carNo);

}
