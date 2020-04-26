package com.zjft.microservice.treasurybrain.managecenter.web_inner;

import com.zjft.microservice.treasurybrain.managecenter.service.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 崔耀中
 * @since 2019-12-31
 */
@Slf4j
@RestController
public class CarInnerResourceImpl implements CarInnerResource{

	@Resource
	private CarService carService;

	@Override
	public String qryCarNumberByNo(Integer carNo){
		return carService.qryCarNumberByNo(carNo);
	}
}
