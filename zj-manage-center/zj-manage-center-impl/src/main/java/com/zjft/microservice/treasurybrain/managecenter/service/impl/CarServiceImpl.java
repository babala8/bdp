package com.zjft.microservice.treasurybrain.managecenter.service.impl;

import com.zjft.microservice.treasurybrain.channelcenter.web_inner.DevServiceCompanyInnerResource;
import com.zjft.microservice.treasurybrain.managecenter.domain.CarInfoDO;
import com.zjft.microservice.treasurybrain.managecenter.dto.CarDTO;
import com.zjft.microservice.treasurybrain.managecenter.mapstruct.CarConverter;
import com.zjft.microservice.treasurybrain.managecenter.po.CarInfoPO;
import com.zjft.microservice.treasurybrain.managecenter.repository.CarMapper;
import com.zjft.microservice.treasurybrain.managecenter.service.CarService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.FileUtil;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 崔耀中
 * @since 2019-09-21
 */
@Slf4j
@Service
public class CarServiceImpl implements CarService {

	@Resource
	private CarMapper carMapper;

	@Resource
	private DevServiceCompanyInnerResource devServiceCompanyInnerResource;

	@Override
	public PageDTO<CarDTO> queryCarList(Map<String, Object> paramMap){
		log.info("------------[queryCarList]CarService-------------");
		PageDTO<CarDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		try {
			Integer type = StringUtil.objectToInt(paramMap.get("type"));
			Integer status = StringUtil.objectToInt(paramMap.get("status"));
			paramMap.put("type", type);
			paramMap.put("status", status);
			int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);

			// 获取数据总条数
			int totalRow = carMapper.qryTotalRowCar(paramMap);
			int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

			//获取分页数据
			List<CarInfoDO> doList = carMapper.queryCarList(paramMap);
			List<CarDTO> dtoList = CarConverter.INSTANCE.domain2dto(doList);
			pageDTO.setTotalRow(totalRow);
			pageDTO.setTotalPage(totalPage);
			pageDTO.setPageSize(dtoList.size());
			pageDTO.setRetList(dtoList);
			pageDTO.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("[queryCarList]Fail", e);
			pageDTO.setResult(RetCodeEnum.EXCEPTION);
		}
		return pageDTO;

	}

	@Override
	public Integer queryByCarNumber(String carNumber){
		log.info("------------[querybyCarNo]CarService-------------");
		return carMapper.queryByCarNumber(carNumber);
	}

	@Override
	public DTO addCarInfo(CarDTO carDTO){
		log.info("------------[addCarInfo]CarService-------------");
		try {
			CarInfoPO carInfoPO = CarConverter.INSTANCE.dto2domain(carDTO);
			Integer carNo = null;
			//查询最大编号
			String maxCarNo = carMapper.getMaxCarNo();
			if(StringUtil.isNullorEmpty(maxCarNo)){
				carNo = 1001;
			}else {
				carNo = Integer.parseInt(maxCarNo)+1;
			}
			//设置最大编号
			carInfoPO.setCarNo(carNo);
			int insert = carMapper.insert(carInfoPO);
			if (insert == 1){
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}catch (Exception e){
			log.error("[addTagInfo] Fail: ", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
		return  new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO modCarInfo(CarDTO carDTO){
		log.info("------------[modCarInfo]CarService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			CarInfoPO carInfoPO = CarConverter.INSTANCE.dto2domain(carDTO);
			int x = carMapper.update(carInfoPO);
			if (x == 1) {
				dto.setResult(RetCodeEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[modCarInfo]Fail", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public DTO delCarInfoByNo(String carNo){
		log.info("------------[delCarInfoByNo]CarService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			int x = carMapper.delCarInfoByNo(carNo);
			if (x == 1) {
				dto.setResult(RetCodeEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[delCarInfoByNo]Fail", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public DTO importCar(String fileName, MultipartFile mfile) {
		String basePath = FileUtil.getBasePath();
		File uploadDir = new  File(basePath+"/template/");
		//创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
		if (!uploadDir.exists()) uploadDir.mkdirs();
		//新建一个文件
		File tempFile = new File(basePath+"/template/" + new Date().getTime() + ".xlsx");
		//根据版本选择创建Workbook的方式
		InputStream is = null;
		Workbook wb = null;
		try{
			//将上传的文件写入新建的文件中
			mfile.transferTo(tempFile);
			//根据新建的文件实例化输入流
			is = new FileInputStream(tempFile);
			//根据版本选择创建Workbook的方式
			if(fileName.endsWith("xls")){     //Excel&nbsp;2003
				wb = new HSSFWorkbook(is);
			}else if(fileName.endsWith("xlsx")){    // Excel 2007/2010
				wb = new XSSFWorkbook(is);
			}
			//根据excel里面的内容读取知识库信息
			return readExcelValue(wb,tempFile);
		}catch(Exception e){
			e.printStackTrace();
			return new DTO(RetCodeEnum.FAIL.getCode(), "车辆导入失败");
		} finally{
			tempFile.delete();
			if(is !=null)
			{
				try{
					is.close();
				}catch(IOException e){
					is = null;
					e.printStackTrace();
				}
			}
		}
	}



	/**
	 * 解析Excel里面的数据
	 * @param wb
	 * @return
	 */
	private DTO readExcelValue(Workbook wb,File tempFile){

		//错误信息接收器
		String errorMsg = "";
		//得到第一个shell
		Sheet sheet=wb.getSheetAt(0);
		//得到Excel的行数
		int totalRows=sheet.getPhysicalNumberOfRows();
		//总列数
		int totalCells = 0;
		//得到Excel的列数(前提是有行数)，从第二行算起
		if(totalRows>=2 && sheet.getRow(1) != null){
			totalCells=sheet.getRow(1).getPhysicalNumberOfCells();
		}
		List<CarInfoPO> carInfoList=new ArrayList<CarInfoPO>();
		CarInfoPO tempCarInfo;

		String br = "<br/>";

		//循环Excel行数,从第二行开始。标题不入库
		for(int r=1;r<totalRows;r++){
			String rowMessage = "";
			Row row = sheet.getRow(r);
			if (row == null){
				errorMsg += br+"第"+(r+1)+"行数据有问题，请仔细检查！";
				continue;
			}
			tempCarInfo = new CarInfoPO();

			//String carNo = "";
			String type = "";
			String carNumber = "";
			String status = "";
			String company = "";
			String signingType = "";
			String maxDuration = "";
			String maxMileage = "";

			//循环Excel的列
			for(int c = 0; c <totalCells; c++){
				Cell cell = row.getCell(c);
				if(cell!=null){
					cell.setCellType(Cell.CELL_TYPE_STRING);
				}else {
					rowMessage += "第一列（车辆类型）为空；";
				}
				if (null != cell){
					if(c==0){
						type = cell.getStringCellValue();
						if(type.length()>38){
							rowMessage += "车辆类型超过指定长度；";
						}else if(StringUtil.isEmpty(type)){
							rowMessage += "车辆类型不能为空；";
						}else if ("小型".equals(type)){
							type = "0";
						}else if ("中型".equals(type)){
							type = "1";
						}else if ("大型".equals(type)){
							type = "2";
						}else {
							rowMessage += "车辆类型错误；";
						}
						if ("0".equals(type) || "1".equals(type) || "2".equals(type)){
							tempCarInfo.setType(new Integer(type));
						}
					}else if(c==1){
						carNumber=cell.getStringCellValue();
						if(carNumber.length()>100){
							rowMessage += "车牌号超过指定长度；";
						}else if(StringUtil.isEmpty(carNumber)){
							rowMessage += "车牌号不能为空；";
						}
						tempCarInfo.setCarNumber(carNumber);
					}else if(c==2){
						status=cell.getStringCellValue();
						if(status.length()>38){
							rowMessage += "车辆状态超过指定长度；";
						}else if(StringUtil.isEmpty(status)){
							rowMessage += "车辆状态不能为空；";
						}else if ("故障".equals(status)){
							status = "0";
						}else if ("正常".equals(status)){
							status = "1";
						}else {
							rowMessage += "车辆状态错误；";
						}
						if ("0".equals(status) || "1".equals(status)){
							tempCarInfo.setStatus(Integer.valueOf(status));
						}
					}else if(c==3){
						company=cell.getStringCellValue();
						if(company.length()>100){
							rowMessage += "公司超过指定长度；";
						}
						if (company.length()!=0){
							company = devServiceCompanyInnerResource.qryNoByName(company);
						}
						tempCarInfo.setCompany(company);
					}else if(c==4){
						signingType=cell.getStringCellValue();
						if(signingType.length()>38){
							rowMessage += "签约类型超过指定长度；";
						}
						if(signingType.length()!=0){
							if ("计次".equals(signingType)){
								signingType = "0";
							}else if ("月付".equals(signingType)){
								signingType = "1";
							}else if ("里程付".equals(signingType)){
								signingType = "2";
							}
							if ("0".equals(signingType) || "1".equals(signingType) || "2".equals(signingType)){
								tempCarInfo.setSigningType(new Integer(signingType));
							}else {
								rowMessage += "签约类型错误；";
							}
						}
					}else if(c==5){
						maxDuration = cell.getStringCellValue();
						if(maxDuration.length()>100){
							rowMessage += "最大时间超过指定长度；";
						}
						tempCarInfo.setMaxDuration(maxDuration);
					}else if(c==6){
						maxMileage = cell.getStringCellValue();
						if(maxMileage.length()>100){
							rowMessage += "最大里程超过指定长度；";
						}
						tempCarInfo.setMaxMileage(maxMileage);
					}
				}else{
					rowMessage += "第"+(c+1)+"列数据有问题，请仔细检查；";
				}
			}
			//拼接每行的错误提示
			if(!StringUtil.isEmpty(rowMessage)){
				errorMsg += br+"第"+(r+1)+"行，"+rowMessage;
			}else{
				carInfoList.add(tempCarInfo);
			}
		}

		//删除上传的临时文件
		if(tempFile.exists()){
			tempFile.delete();
		}

		//全部验证通过才导入到数据库
		if(StringUtil.isEmpty(errorMsg)){
			for(CarInfoPO carInfoPO : carInfoList){
				carMapper.insertOrUpdate(carInfoPO);
			}
			errorMsg = "导入成功，共"+carInfoList.size()+"条数据！";
			return new DTO(RetCodeEnum.SUCCEED.getCode(),errorMsg);
		}
		return new DTO(RetCodeEnum.FAIL.getCode(),errorMsg);
	}

	@Override
	public List<CarDTO> qryAllCar() {
		List<CarInfoPO> carInfoPOS = carMapper.qryAllCar();
		List<CarDTO> carDTOS = CarConverter.INSTANCE.po2dto(carInfoPOS);
		return carDTOS;
	}

	@Override
	public String qryCarNumberByNo(Integer carNo){
		return carMapper.qryCarNumberByNo(carNo);
	}

}
