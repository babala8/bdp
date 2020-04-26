package com.zjft.microservice.treasurybrain.usercenter.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.FileUtil;
import com.zjft.microservice.treasurybrain.common.util.PageUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.usercenter.dto.OutsourcingDTO;
import com.zjft.microservice.treasurybrain.usercenter.mapstruct.OutsourcingConverter;
import com.zjft.microservice.treasurybrain.usercenter.po.OutsourcingPO;
import com.zjft.microservice.treasurybrain.usercenter.repository.OutsourcingMapper;
import com.zjft.microservice.treasurybrain.usercenter.service.OutsourcingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFCell;
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
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/9/21
 */
@Slf4j
@Service
public class OutsourcingServiceImpl implements OutsourcingService {

	@Resource
	private OutsourcingMapper outsourcingMapper;

	@Override
	public PageDTO<OutsourcingDTO> queryOutsourcingList(Map<String, Object> paramMap) {
		PageDTO<OutsourcingDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		int totalRow = outsourcingMapper.qryTotalRowOutsourcing(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<OutsourcingPO> list = outsourcingMapper.queryOutsourcingByPage(paramMap);
		List<OutsourcingDTO> entityList = OutsourcingConverter.INSTANCE.po2dto(list);

		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setRetList(entityList);
		return pageDTO;
	}

	@Override
	public Integer queryByNo(String no) {
		log.info("------------OutsourcingServiceImpl[queryByNo]-------------");
		return outsourcingMapper.queryByNo(no);
	}

	@Override
	public DTO addOutsourcingInfo(OutsourcingDTO outsourcingDTO) {
		log.info("------------OutsourcingServiceImpl[addOutsourcingInfo]-------------");
		OutsourcingPO outsourcingPO = OutsourcingConverter.INSTANCE.dto2po(outsourcingDTO);
		int insert = outsourcingMapper.insertSelective(outsourcingPO);
		if(insert < 1){
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO modOutsourcingInfo(OutsourcingDTO outsourcingDTO) {
		log.info("------------OutsourcingServiceImpl[modOutsourcingInfo]-------------");
		OutsourcingPO outsourcingPO = OutsourcingConverter.INSTANCE.dto2po(outsourcingDTO);
		int update = outsourcingMapper.updateByPrimaryKeySelective(outsourcingPO);
		if(update < 1){
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO delOutsourcingInfoByNo(String no) {
		log.info("------------OutsourcingServiceImpl[delOutsourcingInfoByNo]-------------");
		int delete = outsourcingMapper.deleteByPrimaryKey(no);
		if(delete < 1){
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO importOutsourcing(String fileName, MultipartFile file) {
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
			file.transferTo(tempFile);
			//根据新建的文件实例化输入流
			is = new FileInputStream(tempFile);
			//根据版本选择创建Workbook的方式
			if(fileName.endsWith("xls")){     //Excel&nbsp;2003
				wb = new HSSFWorkbook(is);
			}else if(fileName.endsWith("xlsx")){    // Excel 2007/2010
				wb = new XSSFWorkbook(is);
			}else{
				return new DTO(RetCodeEnum.FAIL.getCode(), "请检查导入文件格式是否为xls或xlsx");
			}
			//根据excel里面的内容读取知识库信息
			return readExcelValue(wb,tempFile);
		}catch(Exception e){
			e.printStackTrace();
			return new DTO(RetCodeEnum.FAIL.getCode(), "导入失败");
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
		List<OutsourcingPO> outsourcingInfoList=new ArrayList<OutsourcingPO>();
		OutsourcingPO tempTagInfo;

		String br = "<br/>";

		//循环Excel行数,从第二行开始。标题不入库
		for(int r=1;r<totalRows;r++){
			String rowMessage = "";
			Row row = sheet.getRow(r);
			if (row == null){
				errorMsg += br+"第"+(r+1)+"行数据有问题，请仔细检查！";
				continue;
			}
			tempTagInfo = new OutsourcingPO();

			String no = "";
			String name = "";
			String post = "";
			String age = "";
			String familyAddr = "";
			String residenceAddr = "";
			String mobile = "";

			//循环Excel的列
			for(int c = 0; c <totalCells; c++){
				Cell cell = row.getCell(c);
				if ((null != cell) && (!("").equals(cell)) && (cell.getCellType() != HSSFCell.CELL_TYPE_BLANK)){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(c==0){
						no = cell.getStringCellValue();
						if(StringUtil.isEmpty(no)){
							rowMessage += "no不能为空；";
						}else if(no.length()>20){
							rowMessage += "no超过指定长度；";
						}
						tempTagInfo.setNo(no);
					}else if(c==1){
						name = cell.getStringCellValue();
						if(StringUtil.isEmpty(name)){
							rowMessage += "name不能为空；";
						}else if(name.length()>50){
							rowMessage += "name超过指定长度；";
						}
						tempTagInfo.setName(name);
					}else if(c==2){
						post=cell.getStringCellValue();
						if(post.length()>100){
							rowMessage += "post超过指定长度；";
						}
						if(post.length()!=0){
							if(post.equals("外包人员")){
								tempTagInfo.setPost(0);
							}else if(post.equals("安保人员")){
								tempTagInfo.setPost(1);
							}else if(post.equals("车辆驾驶员")){
								tempTagInfo.setPost(2);
							}else{
								rowMessage += "不存在" + post + "的post类型；";
							}
						}
					}else if(c==3){
						age=cell.getStringCellValue();
						if(age.length()>3){
							rowMessage += "age超过指定长度；";
						}
						if(age.length()!=0){
							tempTagInfo.setAge(new Integer(age));
						}
					}else if(c==4){
						familyAddr=cell.getStringCellValue();
						if(familyAddr.length()>200){
							rowMessage += "familyAddr超过指定长度；";
						}
						tempTagInfo.setFamilyAddr(familyAddr);
					}else if(c==5){
						residenceAddr=cell.getStringCellValue();
						if(residenceAddr.length()>200){
							rowMessage += "residenceAddr超过指定长度；";
						}
						tempTagInfo.setResidenceAddr(residenceAddr);
					}else if(c==6){
						mobile = cell.getStringCellValue();
						if(mobile.length()>30){
							rowMessage += "mobile超过指定长度；";
						}
						tempTagInfo.setMobile(mobile);
					}
				}else if(c==0||c==1){
					rowMessage += "第"+(c+1)+"列数据不能为空，请仔细检查；";
					return new DTO(RetCodeEnum.FAIL.getCode(),rowMessage);
				}
			}
			//拼接每行的错误提示
			if(!StringUtil.isEmpty(rowMessage)){
				errorMsg += br+"第"+(r+1)+"行，"+rowMessage;
			}else{
				outsourcingInfoList.add(tempTagInfo);
			}
		}

		//删除上传的临时文件
		if(tempFile.exists()){
			tempFile.delete();
		}

		//全部验证通过才导入到数据库
		if(StringUtil.isEmpty(errorMsg)){
			for(OutsourcingPO outsourcingPO : outsourcingInfoList){
				outsourcingMapper.insertOrUpdate(outsourcingPO);
			}
			errorMsg = "外包人员信息导入成功，共"+outsourcingInfoList.size()+"条数据！";
			return new DTO(RetCodeEnum.SUCCEED.getCode(),errorMsg);
		}else{
			return new DTO(RetCodeEnum.FAIL.getCode(),errorMsg);
		}
	}

	@Override
	public List<String> qryByPost(int post){
		return outsourcingMapper.qryByPost(post);
	}

}
