package com.zjft.microservice.treasurybrain.managecenter.repository;


import com.zjft.microservice.treasurybrain.managecenter.domain.CarInfoDO;
import com.zjft.microservice.treasurybrain.managecenter.po.CarInfoPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2019/9/21
 */
@Mapper
public interface CarMapper {
	/**
	 * 分页查询车辆信息列表
	 * @param paramMap
	 */
	List<CarInfoDO> queryCarList(Map<String, Object> paramMap);

	/**
	 * 查询符合查询条件的车辆总数
	 * @param paramMap
	 */
	int qryTotalRowCar(Map<String, Object> paramMap);

	/**
	 * 查询该车辆编号是否存在
	 * @param carNumber
	 */
	int queryByCarNumber(@Param("carNumber") String carNumber);

	/**
	 * 添加车辆信息
	 * @param carInfoPO
	 */
	int insert(CarInfoPO carInfoPO);

	/**
	 * 修改车辆信息
	 * @param
	 */
	int update(CarInfoPO carInfoPO);

	/**
	 * 修改车辆信息
	 * @param carNo
	 */
	int delCarInfoByNo(String carNo);

	/**
	 * 增加车辆信息
	 * @param carInfoPO
	 */
	int insertOrUpdate(CarInfoPO carInfoPO);

	List<CarInfoPO> qryAllCar();

	/**
	 * @Description 根据车辆编号查找车牌号
	 * @Param carNo
	 */
	String qryCarNumberByNo(Integer carNo);



	String getMaxCarNo();

}
