package com.zjft.microservice.treasurybrain.managecenter.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjft.microservice.treasurybrain.managecenter.domain.BaseEntityInfoDO;
import com.zjft.microservice.treasurybrain.managecenter.dto.BaseEntityDTO;
import com.zjft.microservice.treasurybrain.managecenter.dto.BaseEntityInfoDTO;
import com.zjft.microservice.treasurybrain.managecenter.mapstruct.BaseEntityConverter;
import com.zjft.microservice.treasurybrain.managecenter.po.BaseEntityInfoPO;
import com.zjft.microservice.treasurybrain.managecenter.repository.EntityBaseInfoMapper;
import com.zjft.microservice.treasurybrain.managecenter.service.EntityBaseInfoService;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.dto.PageDTO;
import com.zjft.microservice.treasurybrain.common.util.*;
import com.zjft.microservice.treasurybrain.productcenter.dto.ProductPropertyKeyValueDTO;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ProductInnerResource;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ProductPropertyInnerResource;
import com.zjft.microservice.treasurybrain.productcenter.web_inner.ProductPropertyKeyInnerResource;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author 葛瑞莲
 * @since 2019/9/11
 */
@Slf4j
@Service
public class EntityBaseInfoServiceImpl implements EntityBaseInfoService {

	@Resource
	private EntityBaseInfoMapper entityBaseInfoMapper;

	@Resource
	private ProductPropertyInnerResource productPropertyInnerResource;

	@Resource
	private ProductPropertyKeyInnerResource productPropertyKeyInnerResource;

	@Resource
	private ProductInnerResource productInnerResource;

	/**
	 * 根据网点查询款箱号
	 *
	 * @return 结果
	 */
	@Override
	public List<String> qryContainerNoList(String customerNo) {
		return entityBaseInfoMapper.qryContainerNoList(customerNo);
	}

	@Override
	public PageDTO<BaseEntityInfoDTO> queryEntityByPage(Map<String, Object> paramMap) {
		PageDTO<BaseEntityInfoDTO> pageDTO = new PageDTO<>(RetCodeEnum.SUCCEED);
		int pageSize = PageUtil.transParam2Page(paramMap, pageDTO);
		int totalRow = entityBaseInfoMapper.qryTotalRowEntity(paramMap);
		int totalPage = PageUtil.computeTotalPage(pageSize, totalRow);

		List<BaseEntityInfoDO> list = entityBaseInfoMapper.queryEntityByPage(paramMap);
		List<BaseEntityInfoDTO> entityList = BaseEntityConverter.INSTANCE.domain2dto(list);
		entityList.forEach(baseEntityInfoDTO -> baseEntityInfoDTO.setPramsList(JSONObject.parseObject(baseEntityInfoDTO.getPrams(),Map.class)));

		pageDTO.setTotalPage(totalPage);
		pageDTO.setTotalRow(totalRow);
		pageDTO.setRetList(entityList);
		return pageDTO;
	}

	@Override
	public Integer queryByEntityNo(String entityNo) {
		return entityBaseInfoMapper.queryByEntityNo(entityNo);
	}

	@Override
	public DTO addEntityInfo(BaseEntityDTO baseEntityDTO) {
		BaseEntityInfoPO baseEntityInfoPO = new BaseEntityInfoPO();
		String goodsNo = baseEntityDTO.getGoodsNo();
		baseEntityInfoPO.setEntityNo(baseEntityDTO.getEntityNo());
		baseEntityInfoPO.setGoodsNo(goodsNo);
		//根据物品编号查询并拼接对应的属性json字符串
//		String prams = "";
//		List<GoodsPropertyKeyDO> goodsPropertyKeyDOS = entityBaseInfoMapper.qryKeysByGoodsNo(goodsNo);
//		if(goodsPropertyKeyDOS.size() > 0){
//			for (int i = 0; i < goodsPropertyKeyDOS.size(); i++) {
//				String key = goodsPropertyKeyDOS.get(i).getPropertyNo();
//				String keyName = goodsPropertyKeyDOS.get(i).getPropertyName();
//				List<String> values = entityBaseInfoMapper.qryValuesByKey(key);
//				if(values.size()>0){
//					if(i == 0){
//						prams += "{";
//					}
//					prams += "\"" + keyName + "\":[";
//					for (int j = 0; j < values.size(); j++) {
//						prams += "\"" + values.get(j) + "\"";
//						prams += (j == values.size()-1)?"]":",";
//					}
//					prams += (i == goodsPropertyKeyDOS.size()-1)?"}":",";
//				}
//			}
//		}
		String prams = JSONUtil.createJsonString(baseEntityDTO.getGoodsPropertyList());
		baseEntityInfoPO.setPrams(prams);
		baseEntityInfoPO.setCustomerNo(baseEntityDTO.getCustomerNo());
		baseEntityInfoPO.setCustomerType(baseEntityDTO.getCustomerType());
		baseEntityInfoPO.setCreateTime(CalendarUtil.getSysTimeYMDHMS());
		int insert = entityBaseInfoMapper.insert(baseEntityInfoPO);
		if(insert < 1){
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO modEntityInfo(BaseEntityDTO baseEntityDTO) {
		BaseEntityInfoPO baseEntityInfoPO = new BaseEntityInfoPO();
		String goodsNo = baseEntityDTO.getGoodsNo();
		baseEntityInfoPO.setEntityNo(baseEntityDTO.getEntityNo());
		baseEntityInfoPO.setGoodsNo(goodsNo);
		//根据物品编号查询并拼接对应的属性json字符串
//		String prams = "";
//		List<GoodsPropertyKeyDO> goodsPropertyKeyDOS = entityBaseInfoMapper.qryKeysByGoodsNo(goodsNo);
//		if(goodsPropertyKeyDOS.size() > 0){
//			for (int i = 0; i < goodsPropertyKeyDOS.size(); i++) {
//				String key = goodsPropertyKeyDOS.get(i).getPropertyNo();
//				String keyName = goodsPropertyKeyDOS.get(i).getPropertyName();
//				List<String> values = entityBaseInfoMapper.qryValuesByKey(key);
//				if(values.size()>0){
//					if(i == 0){
//						prams += "{";
//					}
//					prams += "\"" + keyName + "\":[";
//					for (int j = 0; j < values.size(); j++) {
//						prams += "\"" + values.get(j) + "\"";
//						prams += (j == values.size()-1)?"]":",";
//					}
//					prams += (i == goodsPropertyKeyDOS.size()-1)?"}":",";
//				}
//			}
//		}
		String prams = JSONUtil.createJsonString(baseEntityDTO.getGoodsPropertyList());
		baseEntityInfoPO.setPrams(prams);
		baseEntityInfoPO.setCustomerNo(baseEntityDTO.getCustomerNo());
		baseEntityInfoPO.setCustomerType(baseEntityDTO.getCustomerType());
		baseEntityInfoPO.setModTime(CalendarUtil.getSysTimeYMDHMS());
		int update = entityBaseInfoMapper.update(baseEntityInfoPO);
		if(update < 1){
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO delEntityInfoByEntityNo(String entityNo) {
		int x = entityBaseInfoMapper.delete(entityNo);
		if(x != 1){
			return new DTO(RetCodeEnum.FAIL);
		}
		return new DTO(RetCodeEnum.SUCCEED);
	}

	@Override
	public DTO importEntity(String fileName, MultipartFile mfile) {
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
			return new DTO(RetCodeEnum.FAIL.getCode(), "导入容器失败");
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
	@Transactional(rollbackFor = Exception.class)
	public DTO readExcelValue(Workbook wb,File tempFile){

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
		List<BaseEntityInfoPO> entityInfoList=new ArrayList<BaseEntityInfoPO>();
		BaseEntityInfoPO tempEntityInfo;

		String br = "<br/>";

		//循环Excel行数,从第二行开始。标题不入库
		for(int r=1;r<totalRows;r++){
			String rowMessage = "";
			Row row = sheet.getRow(r);
			if (row == null){
				errorMsg += br+"第"+(r+1)+"行数据有问题，请仔细检查！";
				continue;
			}
			tempEntityInfo = new BaseEntityInfoPO();

			String entityNo = "";
			String goodsNo = "";
			String prams = "";
			String customerNo = "";
			String customerType = "";

			//循环Excel的列
			for(int c = 0; c <totalCells; c++){
				Cell cell = row.getCell(c);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (null != cell){
					if(c==0){
						entityNo = cell.getStringCellValue();
						if(StringUtil.isEmpty(entityNo)){
							rowMessage += "entityNo不能为空；";
						}else if(entityNo.length()>32){
							rowMessage += "entityNo超过指定长度；";
						}
						tempEntityInfo.setEntityNo(entityNo);
					}else if(c==1){
						goodsNo = cell.getStringCellValue();
						if(goodsNo.length()>32){
							rowMessage += "goodsNo超过指定长度；";
						}
						tempEntityInfo.setGoodsNo(goodsNo);
					}else if(c==2){
						prams=cell.getStringCellValue();
						if(prams.length()>200){
							rowMessage += "prams超过指定长度；";
						}
						tempEntityInfo.setPrams(prams);
					}else if(c==3){
						customerNo=cell.getStringCellValue();
						if(customerNo.length()>32){
							rowMessage += "customerNo超过指定长度；";
						}
						tempEntityInfo.setCustomerNo(customerNo);
					}else if(c==4){
						customerType=cell.getStringCellValue();
						if(customerType.length()>38){
							rowMessage += "customerType超过指定长度；";
						}
						if(customerType.length()!=0){
							tempEntityInfo.setCustomerType(new Integer(customerType));
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
				entityInfoList.add(tempEntityInfo);
			}
		}

		//删除上传的临时文件
		if(tempFile.exists()){
			tempFile.delete();
		}

		//全部验证通过才导入到数据库
		if(StringUtil.isEmpty(errorMsg)){
			for(BaseEntityInfoPO baseEntityInfoPO : entityInfoList){
				int count = entityBaseInfoMapper.queryByEntityNo(baseEntityInfoPO.getEntityNo());
				int isExist = productInnerResource.qryExist(baseEntityInfoPO.getGoodsNo());
				if(isExist==0){
					throw new RuntimeException("该物品类型不存在");
				}
				//物品表里不存在该物品,则插入
				if(count==0){
					baseEntityInfoPO.setModTime(CalendarUtil.getSysTimeYMDHMS());
					String prams = baseEntityInfoPO.getPrams();
					Map map = JSON.parseObject(prams);
					for(Object key:map.keySet()) {
						//获取key向GOODS_PROPERTY_KEY表中插入数据
						//GoodsPropertyKeyPO goodsPropertyKeyPO=new GoodsPropertyKeyPO();
						ProductPropertyKeyValueDTO productPropertyKeyValueDTO = new ProductPropertyKeyValueDTO();
						String maxNo = productPropertyKeyInnerResource.selectMaxNo();
						Integer tmpInt = Integer.parseInt(maxNo) + 1;
						String newPropertyNo = String.format("%04d", tmpInt);
						String propertyName = map.get(key).toString();
						/*goodsPropertyKeyPO.setPropertyNo(newPropertyNo);
						goodsPropertyKeyPO.setGoodsNo(baseEntityInfoPO.getGoodsNo());
						goodsPropertyKeyPO.setPropertyName(key.toString());
						goodsPropertyKeyPO.setCreateTime(CalendarUtil.getSysTimeYMD());*/
						productPropertyKeyValueDTO.setPropertyNo(newPropertyNo);
						productPropertyKeyValueDTO.setProductNo(baseEntityInfoPO.getGoodsNo());
						productPropertyKeyValueDTO.setPropertyName(key.toString());
						productPropertyKeyValueDTO.setCreateTime(CalendarUtil.getSysTimeYMD());

						Map<String,Object> goodsPropertyKeyValue = new HashMap<>();
						goodsPropertyKeyValue.put("propertyNo",newPropertyNo);
						goodsPropertyKeyValue.put("goodsNo",baseEntityInfoPO.getGoodsNo());
						goodsPropertyKeyValue.put("propertyName",key.toString());
						goodsPropertyKeyValue.put("createTime",CalendarUtil.getSysTimeYMD());

						int i = productPropertyKeyInnerResource.selectCountByGoodsNo(baseEntityInfoPO.getGoodsNo(), key.toString());
						if(i==0){
							//如果GOODS_PROPERTY_KEY中没有该记录
							//int flag1=igoodsPropertyKeyMapper.insert(goodsPropertyKeyPO);
//							int flag1=goodsPropertyKeyResource.insert(productPropertyKeyValueDTO);
							int flag1= productPropertyKeyInnerResource.insert(goodsPropertyKeyValue);
							if(flag1!=1){
								throw new RuntimeException("GOODS_PROPERTY_KEY插入失败");
							}
							//获取value向GOODS_PROPERTY_VALUE表中插入数据
							String propertyValue = map.get(key).toString();
							List<String> valueList = Arrays.asList(propertyValue);
							for(String value:valueList){
								value=value.replaceAll("\"|\\[|\\]","");
								if(value.contains(",")){
									String[] arr=value.split(",");
									for(int k=0;k<arr.length;k++){
										String id = UUID.randomUUID().toString().replace("-", "");
										/*GoodsPropertyValuePO goodsPropertyValuePO=new GoodsPropertyValuePO();
										goodsPropertyValuePO.setId(id);
										goodsPropertyValuePO.setPropertyNo(newPropertyNo);
										goodsPropertyValuePO.setPropertyValue(arr[k]);
										goodsPropertyValuePO.setCreateTime(CalendarUtil.getSysTimeYMD());*/
//										GoodsProperty2DTO goodsProperty2DTO = new GoodsProperty2DTO();
//										goodsProperty2DTO.setId(id);
//										goodsProperty2DTO.setPropertyNo(newPropertyNo);
//										goodsProperty2DTO.setPropertyValue(arr[k]);
//										goodsProperty2DTO.setCreateTime(CalendarUtil.getSysTimeYMD());

										Map<String,Object> GoodsProperty = new HashMap<>();
										GoodsProperty.put("id",id);
										GoodsProperty.put("propertyNo",newPropertyNo);
										GoodsProperty.put("propertyValue",arr[k]);
										GoodsProperty.put("createTime",CalendarUtil.getSysTimeYMD());
										int j = productPropertyInnerResource.selectByNoAndValue(newPropertyNo, value);
										if(j==0){
											//int flag2=igoodsPropertyValueMapper.insert(goodsPropertyValuePO);
//											int flag2=productPropertyInnerResource.insert(goodsProperty2DTO);
											int flag2= productPropertyInnerResource.insert(GoodsProperty);
											if(flag2!=1){
												throw new RuntimeException("GOODS_PROPERTY_VALUE插入失败");
											}
										}
									}
								}else{
									String id = UUID.randomUUID().toString().replace("-", "");
									/*GoodsPropertyValuePO goodsPropertyValuePO=new GoodsPropertyValuePO();
									goodsPropertyValuePO.setId(id);
									goodsPropertyValuePO.setPropertyNo(newPropertyNo);
									goodsPropertyValuePO.setPropertyValue(value);
									goodsPropertyValuePO.setCreateTime(CalendarUtil.getSysTimeYMD());*/
//									GoodsProperty2DTO goodsProperty2DTO = new GoodsProperty2DTO();
//									goodsProperty2DTO.setId(id);
//									goodsProperty2DTO.setPropertyNo(newPropertyNo);
//									goodsProperty2DTO.setPropertyValue(value);
//									goodsProperty2DTO.setCreateTime(CalendarUtil.getSysTimeYMD());

									Map<String,Object> GoodsProperty = new HashMap<>();
									GoodsProperty.put("id",id);
									GoodsProperty.put("propertyNo",newPropertyNo);
									GoodsProperty.put("propertyValue",value);
									GoodsProperty.put("createTime",CalendarUtil.getSysTimeYMD());
									int j = productPropertyInnerResource.selectByNoAndValue(newPropertyNo, value);
									if(j==0){
										//int flag2=igoodsPropertyValueMapper.insert(goodsPropertyValuePO);
//										int flag2=productPropertyInnerResource.insert(goodsProperty2DTO);
										int flag2= productPropertyInnerResource.insert(GoodsProperty);
										if(flag2!=1){
											throw new RuntimeException("GOODS_PROPERTY_VALUE插入失败");
										}
									}
								}

							}
						}else{
							//如果GOODS_PROPERTY_KEY中没有该记录
							String existPropertyNo = productPropertyKeyInnerResource.selectPropertyNoByGoodsNo(baseEntityInfoPO.getGoodsNo(), key.toString());
							//获取value向GOODS_PROPERTY_VALUE表中插入数据
							String propertyValue = map.get(key).toString();
							List<String> valueList = Arrays.asList(propertyValue);
							for(String value:valueList){
								value=value.replaceAll("\"|\\[|\\]","");
								if(value.contains(",")) {
									String[] arr = value.split(",");
									for (int k = 0; k < arr.length; k++) {
										String id = UUID.randomUUID().toString().replace("-", "");
										/*GoodsPropertyValuePO goodsPropertyValuePO = new GoodsPropertyValuePO();
										goodsPropertyValuePO.setId(id);
										goodsPropertyValuePO.setPropertyNo(existPropertyNo);
										goodsPropertyValuePO.setPropertyValue(arr[k]);
										goodsPropertyValuePO.setCreateTime(CalendarUtil.getSysTimeYMD());*/
//										GoodsProperty2DTO goodsProperty2DTO = new GoodsProperty2DTO();
//										goodsProperty2DTO.setId(id);
//										goodsProperty2DTO.setPropertyNo(newPropertyNo);
//										goodsProperty2DTO.setPropertyValue(arr[k]);
//										goodsProperty2DTO.setCreateTime(CalendarUtil.getSysTimeYMD());

										Map<String,Object> GoodsProperty = new HashMap<>();
										GoodsProperty.put("id",id);
										GoodsProperty.put("propertyNo",newPropertyNo);
										GoodsProperty.put("propertyValue",value);
										GoodsProperty.put("createTime",CalendarUtil.getSysTimeYMD());
										int j = productPropertyInnerResource.selectByNoAndValue(newPropertyNo, value);
										if (j == 0) {
											//int flag3 = igoodsPropertyValueMapper.insert(goodsPropertyValuePO);
//											int flag3 = productPropertyInnerResource.insert(goodsProperty2DTO);
											int flag3= productPropertyInnerResource.insert(GoodsProperty);
											if (flag3 != 1) {
												throw new RuntimeException("GOODS_PROPERTY_VALUE插入失败");
											}
										}
									}
								}else{
									String id = UUID.randomUUID().toString().replace("-", "");
									/*GoodsPropertyValuePO goodsPropertyValuePO = new GoodsPropertyValuePO();
									goodsPropertyValuePO.setId(id);
									goodsPropertyValuePO.setPropertyNo(existPropertyNo);
									goodsPropertyValuePO.setPropertyValue(value);
									goodsPropertyValuePO.setCreateTime(CalendarUtil.getSysTimeYMD());*/
//									GoodsProperty2DTO goodsProperty2DTO = new GoodsProperty2DTO();
//									goodsProperty2DTO.setId(id);
//									goodsProperty2DTO.setPropertyNo(newPropertyNo);
//									goodsProperty2DTO.setPropertyValue(value);
//									goodsProperty2DTO.setCreateTime(CalendarUtil.getSysTimeYMD());

									Map<String,Object> GoodsProperty = new HashMap<>();
									GoodsProperty.put("id",id);
									GoodsProperty.put("propertyNo",newPropertyNo);
									GoodsProperty.put("propertyValue",value);
									GoodsProperty.put("createTime",CalendarUtil.getSysTimeYMD());
									int j = productPropertyInnerResource.selectByNoAndValue(newPropertyNo, value);
									if (j == 0) {
										//int flag3 = igoodsPropertyValueMapper.insert(goodsPropertyValuePO);
//										int flag3 = productPropertyInnerResource.insert(goodsProperty2DTO);
										int flag3= productPropertyInnerResource.insert(GoodsProperty);
										if (flag3 != 1) {
											throw new RuntimeException("GOODS_PROPERTY_VALUE插入失败");
										}
									}
								}
							}
						}
					}
					entityBaseInfoMapper.insert(baseEntityInfoPO);
				}else{
					return new DTO(RetCodeEnum.FAIL.getCode(), "该物品已存在");
				}
			}
			errorMsg = "导入成功，共"+entityInfoList.size()+"条数据！";
		}
		return new DTO(RetCodeEnum.SUCCEED.getCode(),"物品导入成功");
	}
}
