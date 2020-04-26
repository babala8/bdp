package com.zjft.microservice.treasurybrain.managecenter.component;

import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPath;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentExitPaths;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentMapping;
import com.zjft.microservice.orchestration.core.annotations.ZjComponentResource;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.managecenter.dto.CarDTO;
import com.zjft.microservice.treasurybrain.managecenter.mapstruct.CarConverter;
import com.zjft.microservice.treasurybrain.managecenter.po.CarInfoPO;
import com.zjft.microservice.treasurybrain.managecenter.repository.CarMapper;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 崔耀中
 * @since 2020-01-09
 */
@Slf4j
@ZjComponentResource(group = "managecenter")
public class CarComponent {

	@Resource
	private CarMapper carMapper;


	/**
	 * @Description 新增车辆信息
	 * @Param
	 */
	@ZjComponentMapping("addCarInfo")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String addCarInfo(CarDTO requestDTO , DTO returnDTO, List<String> no){
		log.info("------------[addCarInfo]CarService-------------");
		try {
			CarInfoPO carInfoPO = CarConverter.INSTANCE.dto2domain(requestDTO);
			int insert = carMapper.insert(carInfoPO);
			if (insert == 1){
				returnDTO.setResult(RetCodeEnum.SUCCEED);
				return "ok";
			}
		}catch (Exception e){
			log.error("[addTagInfo] Fail: ", e);
			returnDTO.setResult(RetCodeEnum.EXCEPTION);
			return "fail";
		}
		returnDTO.setResult(RetCodeEnum.FAIL);
		return "fail";
	}

	/**
	 * @Description 修改车辆信息
	 * @Param
	 */
	@ZjComponentMapping("modCarInfo")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
	public String modCarInfo(CarDTO requestDTO,DTO returnDTO, List<String> no){
		log.info("------------[modCarInfo]CarService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			CarInfoPO carInfoPO = CarConverter.INSTANCE.dto2domain(requestDTO);
			int x = carMapper.update(carInfoPO);
			if (x == 1) {
				returnDTO.setResult(RetCodeEnum.SUCCEED);
				return "ok";
			}
		} catch (Exception e) {
			log.error("[modCarInfo]Fail", e);
			returnDTO.setResult(RetCodeEnum.EXCEPTION);
			return "fail";
		}
		return "fail";
	}

	/**
	 * @Description 删除车辆信息
	 * @Param
	 */
	@ZjComponentMapping("delCarInfoByNo")
	@ZjComponentExitPaths({ // 定义组件的出口
			@ZjComponentExitPath(target = "ok", description = "成功"),
			@ZjComponentExitPath(target = "fail", description = "失败")
	})
		public String delCarInfoByNo(String carNo,DTO returnDTO, List<String> no){
		log.info("------------[delCarInfoByNo]CarService-------------");
		try {
			int x = carMapper.delCarInfoByNo(carNo);
			if (x == 1) {
				returnDTO.setResult(RetCodeEnum.SUCCEED);
				return "ok";
			}
		} catch (Exception e) {
			log.error("[delCarInfoByNo]Fail", e);
			returnDTO.setResult(RetCodeEnum.EXCEPTION);
			return "fail";
		}
		return "fail";
	}

}
