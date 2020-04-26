package com.zjft.microservice.treasurybrain.managecenter.web;

import com.zjft.microservice.treasurybrain.managecenter.dto.CarDTO;
import com.zjft.microservice.treasurybrain.managecenter.service.CarService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.ExcelImportUtils;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2019-09-21
 */
@Slf4j
@RestController
public class CarResourceImpl implements CarResource {

	@Resource
	private CarService carService;

	@Override
	public PageDTO<CarDTO> queryCarList(Map<String, Object> paramMap) {
		log.info("------------[queryCarList]CarResource-------------");
		try {
			return carService.queryCarList(paramMap);
		} catch (Exception e) {
			log.error("查询车辆列表失败", e);
			return new PageDTO<>(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO addCarInfo (CarDTO carDTO){
		log.info("------------[addCarInfo]CarResource-------------");
		try {
			String carNumber = carDTO.getCarNumber();
			if (StringUtil.isNullorEmpty(carNumber)){
				return new DTO(RetCodeEnum.FAIL);
			}else if (carService.queryByCarNumber(carNumber)>0){
				log.error("添加失败，该车牌号已存在");
				return new DTO(RetCodeEnum.FAIL.getCode(),"添加失败，车牌号已存在");
			}
			return carService.addCarInfo(carDTO);
		}catch (Exception e){
			log.error("新增标签信息异常：" + e.getMessage());
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO modCarInfo (CarDTO carDTO){
		log.info("------------[modCarInfo]CarResource-------------");
		try {
			Integer carNo = carDTO.getCarNo();
			if (carNo==null){
				return new DTO(RetCodeEnum.RESULT_EMPTY);
			}
			return carService.modCarInfo(carDTO);
		} catch (Exception e) {
			log.error("修改标签信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO delCarInfoByNo (String carNo){
		log.info("------------[delCarInfoByNo]CarResource-------------");
		try {
			if (StringUtil.isNullorEmpty(carNo)){
				return new DTO(RetCodeEnum.AUDIT_OBJECT_DELETED);
			}
			return carService.delCarInfoByNo(carNo);
		}catch (Exception e){
			log.error("删除车辆信息失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public DTO importCar (MultipartFile file){
		log.info("------------[importTag]TagResource-------------");
		try{
			if(file==null){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件不能为空");
			}

			//验证文件名是否合格
			String fileName=file.getOriginalFilename();
			if(!ExcelImportUtils.validateExcel(fileName)){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件必须是excel格式");
			}

			//进一步判断文件内容是否为空（即判断其大小是否为0或其名称是否为null）
			long size=file.getSize();
			if(StringUtil.isEmpty(fileName) || size==0){
				return new DTO(RetCodeEnum.FAIL.getCode(), "文件不能为空");
			}

			//批量导入
			return carService.importCar(fileName,file);

		} catch (Exception e){
			log.error("批量导入标签失败", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
	}

	@Override
	public List<CarDTO> qryAllCar(){
		return carService.qryAllCar();
	}
}
