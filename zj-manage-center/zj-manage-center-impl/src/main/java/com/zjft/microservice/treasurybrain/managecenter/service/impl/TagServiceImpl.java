package com.zjft.microservice.treasurybrain.managecenter.service.impl;

import com.zjft.microservice.treasurybrain.managecenter.domain.TagInfoDO;
import com.zjft.microservice.treasurybrain.managecenter.dto.TagDTO;
import com.zjft.microservice.treasurybrain.managecenter.mapstruct.TagConverter;
import com.zjft.microservice.treasurybrain.managecenter.po.TagInfoPO;
import com.zjft.microservice.treasurybrain.managecenter.repository.TagMapper;
import com.zjft.microservice.treasurybrain.managecenter.service.TagService;
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
import java.util.Map;

/**
 * @author 葛瑞莲
 * @since 2019/6/11
 */
@Slf4j
@Service
public class TagServiceImpl implements TagService {
	@Resource
	private TagMapper tagMapper;

	@Override
	public PageDTO<TagDTO> queryTagList(Map<String, Object> paramMap) {
		log.info("------------[queryTagList]TagService-------------");
		PageDTO<TagDTO> pageDTO = new PageDTO<>(RetCodeEnum.FAIL);
		try {
			Integer tagType = StringUtil.objectToInt(paramMap.get("tagType"));
			Integer status = StringUtil.objectToInt(paramMap.get("status"));
			paramMap.put("tagType", tagType);
			paramMap.put("status", status);
			int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);

			// 获取数据总条数
			int totalRow = tagMapper.qryTotalRowTag(paramMap);
			int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

			//获取分页数据
			List<TagInfoDO> doList = tagMapper.queryTagListByTagTid(paramMap);
			List<TagDTO> dtoList = TagConverter.INSTANCE.domain2dto(doList);
			pageDTO.setTotalRow(totalRow);
			pageDTO.setTotalPage(totalPage);
			pageDTO.setPageSize(dtoList.size());
			pageDTO.setRetList(dtoList);
			pageDTO.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("[queryTagList]Fail", e);
			pageDTO.setResult(RetCodeEnum.EXCEPTION);
		}
		return pageDTO;
	}

	@Override
	public DTO addTagInfo(TagDTO tagDTO){
		log.info("------------[addTagInfo]TagService-------------");
		try{
			TagInfoPO tagInfoPO = TagConverter.INSTANCE.dto2domain(tagDTO);
			int insert = tagMapper.insert(tagInfoPO);
			if(insert == 1){
				return new DTO(RetCodeEnum.SUCCEED);
			}
		}catch(Exception e){
			log.error("[addTagInfo] Fail: ", e);
			return new DTO(RetCodeEnum.EXCEPTION);
		}
		return new DTO(RetCodeEnum.FAIL);
	}

	@Override
	public DTO modTagInfo(TagDTO tagDTO) {
		log.info("------------[modTagInfo]TagService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			TagInfoPO tagInfoPO = TagConverter.INSTANCE.dto2domain(tagDTO);
			int x = tagMapper.update(tagInfoPO);
			if (x == 1) {
				dto.setResult(RetCodeEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[modTagInfo]Fail", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public DTO delTagInfoByTagTid(String tagTid) {
		log.info("------------[delTagInfoByTagTid]TagService-------------");
		DTO dto = new DTO(RetCodeEnum.FAIL);
		try {
			int x = tagMapper.delTagInfoByTagTid(tagTid);
			if (x == 1) {
				dto.setResult(RetCodeEnum.SUCCEED);
			}
		} catch (Exception e) {
			log.error("[delTagInfoByTagTid]Fail", e);
			dto.setResult(RetCodeEnum.EXCEPTION);
		}
		return dto;
	}

	@Override
	public Integer queryTagStatusByTagTid(String tagTid) {
		log.info("------------[queryTagStatusByTagTid]TagService-------------");
		return tagMapper.queryTagStatusByTagTid(tagTid);
	}

	@Override
	public Integer queryByTagTid(String tagTid) {
		log.info("------------[queryByTagTid]TagService-------------");
		return tagMapper.queryByTagTid(tagTid);
	}

	/**
	 * @author liuyuan
	 * @param tagInfoPO 编号 状态
	 * @return 成功数量
	 */
	@Override
	public Integer updateStatusByTagTid(TagInfoPO tagInfoPO) {
		log.info("------------[updateStatusByTagTid]TagService-------------");
		return tagMapper.updateStatusByTagTid(tagInfoPO);
	}

	@Override
	public DTO importTag(String fileName, MultipartFile mfile) {
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
			return new DTO(RetCodeEnum.FAIL.getCode(), "标签导入失败");
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
	private DTO readExcelValue(Workbook wb, File tempFile){

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
		List<TagInfoPO> tagInfoList=new ArrayList<TagInfoPO>();
		TagInfoPO tempTagInfo;

		String br = "<br/>";

		//循环Excel行数,从第二行开始。标题不入库
		for(int r=1;r<totalRows;r++){
			String rowMessage = "";
			Row row = sheet.getRow(r);
			if (row == null){
				errorMsg += br+"第"+(r+1)+"行数据有问题，请仔细检查！";
				continue;
			}
			tempTagInfo = new TagInfoPO();

			String tagTid = "";
			String epcInfo = "";
			String epcMemorySize = "";
			String tagType = "";
			String status = "";
			String userdataMemorySize = "";
			String note = "";
			String clrCenterNo = "";

			//循环Excel的列
			for(int c = 0; c <totalCells; c++){
				Cell cell = row.getCell(c);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (null != cell){
					if(c==0){
						tagTid = cell.getStringCellValue();
						if(StringUtil.isEmpty(tagTid)){
							rowMessage += "tagTid不能为空；";
						}else if(tagTid.length()>50){
							rowMessage += "tagTid超过指定长度；";
						}
						tempTagInfo.setTagTid(tagTid);
					}else if(c==1){
						epcInfo = cell.getStringCellValue();
						if(epcInfo.length()>32){
							rowMessage += "epcInfo超过指定长度；";
						}
						tempTagInfo.setEpcInfo(epcInfo);
					}else if(c==2){
						epcMemorySize=cell.getStringCellValue();
						if(epcMemorySize.length()>38){
							rowMessage += "epcMemorySize超过指定长度；";
						}
						if(epcMemorySize.length()!=0){
							tempTagInfo.setEpcMemorySize(new Integer(epcMemorySize));
						}
					}else if(c==3){
						tagType=cell.getStringCellValue();
						if(tagType.length()>38){
							rowMessage += "tagType超过指定长度；";
						}else if(StringUtil.isEmpty(tagType)){
							rowMessage += "tagType不能为空；";
						}
						tempTagInfo.setTagType(new Integer(tagType));
					}else if(c==4){
						status=cell.getStringCellValue();
						if(status.length()>38){
							rowMessage += "status超过指定长度；";
						}else if(StringUtil.isEmpty(status)){
							rowMessage += "status不能为空；";
						}
						tempTagInfo.setStatus(new Integer(status));
					}else if(c==5){
						userdataMemorySize=cell.getStringCellValue();
						if(userdataMemorySize.length()>38){
							rowMessage += "userdataMemorySize超过指定长度；";
						}
						if(epcMemorySize.length()!=0){
							tempTagInfo.setUserdataMemorySize(new Integer(userdataMemorySize));
						}
					}else if(c==6){
						note = cell.getStringCellValue();
						if(note.length()>100){
							rowMessage += "note超过指定长度；";
						}
						tempTagInfo.setNote(note);
					}else if(c==7){
						clrCenterNo = cell.getStringCellValue();
						if(clrCenterNo.length()==0){
							tempTagInfo.setClrCenterNo("010211");
						}else if(clrCenterNo.length()>20){
							rowMessage += "clrCenterNo超过指定长度；";
						}
						tempTagInfo.setClrCenterNo(clrCenterNo);
					}
				}else{
					rowMessage += "第"+(c+1)+"列数据有问题，请仔细检查；";
				}
			}
			//拼接每行的错误提示
			if(!StringUtil.isEmpty(rowMessage)){
				errorMsg += br+"第"+(r+1)+"行，"+rowMessage;
			}else{
				tagInfoList.add(tempTagInfo);
			}
		}

		//删除上传的临时文件
		if(tempFile.exists()){
			tempFile.delete();
		}

		//全部验证通过才导入到数据库
		if(StringUtil.isEmpty(errorMsg)){
			for(TagInfoPO tagInfoPO : tagInfoList){
				tagMapper.insertOrUpdate(tagInfoPO);
			}
			errorMsg = "导入成功，共"+tagInfoList.size()+"条数据！";
		}
		return new DTO(RetCodeEnum.SUCCEED.getCode(),"标签导入成功");
	}

}
