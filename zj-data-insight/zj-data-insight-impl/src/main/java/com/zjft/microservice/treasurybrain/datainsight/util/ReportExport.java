package com.zjft.microservice.treasurybrain.datainsight.util;

import com.zjft.microservice.treasurybrain.datainsight.dto.DetailQueryDTO;
import com.zjft.microservice.treasurybrain.common.util.FileUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;



/**
 * 报表输出
 *
 */
public class ReportExport {

    private static Log log = LogFactory.getLog(ReportExport.class);

    /**
     * 用于防止产生实例
     */
    private ReportExport() {

    }

    /**
     * @param infoList     生成表所用到的数据
     * @param parameters   生成表所用到的参数
     */
    public static DetailQueryDTO fixReportMap( List<Map<String, Object>> infoList, Map<String, Object> parameters) throws Exception {
    	DetailQueryDTO dto = new DetailQueryDTO(RetCodeEnum.FAIL.getCode(), "生成失败！");
    	try {
	        String conDir = FileUtil.getBasePath()+"/downloads/";
	
	        String file = UUID.randomUUID().toString();
	      
	        dto.setXlstag(true);
	        dto.setXlsFile(conDir + System.getProperty("file.separator") + file + ".xls");
			
			if(dto.isXlstag()){
				 File destFile = new File(dto.getXlsFile());
				 OutputStream out = new FileOutputStream(destFile);
				 HSSFWorkbook wb = exportXls(infoList,parameters);   
				 wb.write(out);    
			     out.flush();    
			     out.close(); 
			}
			dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
			dto.setRetMsg("生成成功！");
    	} catch (Exception e) {
			log.error("fixreportMap error!"+e.getMessage(),e);
			dto.setRetException("生成异常！");
		}
        return dto;
    }

	public static HSSFWorkbook exportXls(List<Map<String, Object>> infoList,
			Map<String, Object> parameters) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(parameters.get("sheet").toString());
		
		//第一行数据
		List<String> firstRowList = (List<String>)parameters.get("firstRowList");
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < firstRowList.size(); i++) {
			 HSSFCell cell = row.createCell(i);
			 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			 cell.setCellValue(firstRowList.get(i));
		}
		//body数据
		List<String> colRowList = (List<String>)parameters.get("colRowList");
		for (int j = 0; j < infoList.size(); j++) {
			 row = sheet.createRow(j+1);
			 Map<String, Object> map = (Map<String, Object>) infoList.get(j);
			 String valueString = null;
			 for (int m = 0; m < colRowList.size(); m++) {
				 HSSFCell cell = row.createCell(m);
				 valueString  = map.get(colRowList.get(m)).toString();
				 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				 cell.setCellValue(valueString); 
			}
		}
		
		return wb;
	}
}
