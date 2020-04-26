package com.zjft.microservice.treasurybrain.channelcenter.service.impl;


import com.zjft.microservice.treasurybrain.channelcenter.dto.DevServiceCompanyDTO;
import com.zjft.microservice.treasurybrain.channelcenter.mapstruct.DevServiceCompanyConverter;
import com.zjft.microservice.treasurybrain.channelcenter.po.TagInfoPO;
import com.zjft.microservice.treasurybrain.channelcenter.repository.DevServiceCompanyMapper;
import com.zjft.microservice.treasurybrain.channelcenter.service.DevServiceCompanyService;
import com.zjft.microservice.treasurybrain.common.domain.DevServiceCompany;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.FileUtil;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 常健
 * @since 2019-04-04
 */
@Slf4j
@Service
public class DevServiceCompanyServiceImpl implements DevServiceCompanyService {

	@Resource
	private DevServiceCompanyMapper devServiceCompanyMapper;

	@Override
	public PageDTO<DevServiceCompanyDTO> queryDevCompanyList(String name,String type) {
		log.info("------------[queryDevCompanyList]DevServiceCompanyService-------------");
		PageDTO<DevServiceCompanyDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		List<DevServiceCompany> doList = devServiceCompanyMapper.queryDevCompanyListByName(name,type);
		List<DevServiceCompanyDTO> dtoList = DevServiceCompanyConverter.INSTANCE.domain2dto(doList);
		pageDTO.setPageSize(dtoList.size());
		pageDTO.setRetList(dtoList);
		pageDTO.setTotalRow(doList.size());
		int x = dtoList.size()==0?1:dtoList.size();
		int totalPage = PageUtil.computeTotalPage(x,doList.size());
		pageDTO.setTotalPage(totalPage);
		pageDTO.setResult(RetCodeEnum.SUCCEED);
		return pageDTO;
	}

	@Override
	public DTO addDevCompany(DevServiceCompanyDTO devServiceCompanyDTO) {
		log.info("------------[queryDevCompanyList]DevServiceCompanyService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		DevServiceCompany devServiceCompany = DevServiceCompanyConverter.INSTANCE.dto2domain(devServiceCompanyDTO);
		int number = StringUtil.objectToInt(devServiceCompanyMapper.selectMaxNo()) + 1;
		devServiceCompany.setNo(String.valueOf(number));
		int x = devServiceCompanyMapper.insert(devServiceCompany);
		if (x == 1) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}
		return dto;
	}

	@Override
	public DTO modDevCompany(DevServiceCompanyDTO devServiceCompanyDTO) {
		log.info("------------[modDevCompany]DevServiceCompanyService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		DevServiceCompany devServiceCompany = DevServiceCompanyConverter.INSTANCE.dto2domain(devServiceCompanyDTO);
		int x = devServiceCompanyMapper.updateByPrimaryKeySelective(devServiceCompany);
		if (x == 1) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}
		return dto;
	}

	@Override
	public DTO delDevCompanyByNo(String no) {
		log.info("------------[delDevCompanyByNo]DevServiceCompanyService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		int x = devServiceCompanyMapper.deleteByPrimaryKey(no);
		if (x == 1) {
			dto.setResult(RetCodeEnum.SUCCEED);
		}
		return dto;
	}

	@Override
	public DTO importCompany(String fileName, MultipartFile mfile) {
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
			return new DTO(RetCodeEnum.FAIL.getCode(), "服务商导入失败");
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
		List<DevServiceCompany> devServiceCompanyList=new ArrayList<DevServiceCompany>();
		DevServiceCompany tempDevInfo;

		String br = "<br/>";

		//循环Excel行数,从第二行开始。标题不入库
		for(int r=1;r<totalRows;r++){
			String rowMessage = "";
			Row row = sheet.getRow(r);
			if (row == null){
				errorMsg += br+"第"+(r+1)+"行数据有问题，请仔细检查！";
				continue;
			}
			tempDevInfo = new DevServiceCompany();
			int number = StringUtil.objectToInt(devServiceCompanyMapper.selectMaxNo()) + r;
			tempDevInfo.setNo(String.valueOf(number));

			String name = "";
			String linkman = "";
			String address = "";
			String phone = "";
			String mobile = "";
			String fax = "";
			String email = "";
			String type = "";

			//循环Excel的列
			for(int c = 0; c <totalCells; c++){
				Cell cell = row.getCell(c);
				if(cell!=null){
					cell.setCellType(Cell.CELL_TYPE_STRING);
				}
				if (null != cell){
					if(c==0){
						name = cell.getStringCellValue();
						if(StringUtil.isEmpty(name)){
							rowMessage += "name不能为空；";
						}else if(name.length()>80){
							rowMessage += "name超过指定长度；";
						}
						tempDevInfo.setName(name);
					}else if(c==1){
						linkman = cell.getStringCellValue();
						if(linkman.length()>30){
							rowMessage += "linkman超过指定长度；";
						}
						tempDevInfo.setLinkman(linkman);
					}else if(c==2){
						address = cell.getStringCellValue();
						if(address.length()>80){
							rowMessage += "address超过指定长度；";
						}
						tempDevInfo.setAddress(address);
					}else if(c==3){
						phone = cell.getStringCellValue();
						if(phone.length()>30){
							rowMessage += "phone超过指定长度；";
						}
						tempDevInfo.setPhone(phone);
					}else if(c==4){
						mobile = cell.getStringCellValue();
						if(mobile.length()>30){
							rowMessage += "mobile超过指定长度；";
						}
						tempDevInfo.setMobile(mobile);
					}else if(c==5){
						fax = cell.getStringCellValue();
						if(fax.length()>30){
							rowMessage += "fax超过指定长度；";
						}
						tempDevInfo.setFax(fax);
					}else if(c==6){
						email = cell.getStringCellValue();
						if(email.length()>40){
							rowMessage += "email超过指定长度；";
						}
						tempDevInfo.setEmail(email);
					}else if(c==7){
						type=cell.getStringCellValue();
						if(type.length()>10){
							rowMessage += "type超过指定长度；";
						}
						if(type.length()!=0){
							tempDevInfo.setType(new Integer(type));
						}
					}
				}else{
					rowMessage += "第"+(c+1)+"列数据有问题，请仔细检查；";
				}
			}
			//拼接每行的错误提示
			if(!StringUtil.isEmpty(rowMessage)){
				errorMsg += br+"第"+(r+1)+"行，"+rowMessage;
			}else{
				devServiceCompanyList.add(tempDevInfo);
			}
		}

		//删除上传的临时文件
		if(tempFile.exists()){
			tempFile.delete();
		}

		//全部验证通过才导入到数据库
		if(StringUtil.isEmpty(errorMsg)){

			for(DevServiceCompany devServiceCompany : devServiceCompanyList){
				devServiceCompanyMapper.insert(devServiceCompany);
			}
			errorMsg = "导入成功，共"+devServiceCompanyList.size()+"条数据！";
		}
		return new DTO(RetCodeEnum.SUCCEED.getCode(),"标签导入成功");
	}

	@Override
	public String qryNoByName(String name){
		return devServiceCompanyMapper.qryNoByName(name);
	}

}
