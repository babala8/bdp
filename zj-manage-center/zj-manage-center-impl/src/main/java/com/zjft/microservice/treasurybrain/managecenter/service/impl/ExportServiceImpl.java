package com.zjft.microservice.treasurybrain.managecenter.service.impl;

import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.FileUtil;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.managecenter.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author 崔耀中
 * @since 2020-01-15
 */
@Slf4j
@Service
public class ExportServiceImpl implements ExportService {

	@Override
	public DTO exportTemplatesExcel(HttpServletRequest request, HttpServletResponse response, String fileName) {
		DTO dto = new DTO();
		//FileOutputStream output = null;
		try {

			//fileName = "Car.xls";
			String moduleFileFullName = FileUtil.getBasePath() + "//template//"+fileName;

			//FileInputStream fis = new FileInputStream(new File(moduleFileFullName));
			//HSSFWorkbook wb = new HSSFWorkbook(fis);
			//output = new FileOutputStream(outPath + fileName);
			//wb.write(output);

			FileUtil.downLoadResponseFile(request, response, fileName, moduleFileFullName, false, false);
			dto.setResult(RetCodeEnum.SUCCEED);
		} catch (Exception e) {
			log.error("下载异常",e);
			dto.setResult(RetCodeEnum.EXCEPTION);
			dto.setRetMsg(e.getMessage());
		}
		/*finally {
			try {
				//output.close();
				// 打开资源管理器到指定文件夹
				Runtime.getRuntime().exec("cmd /c start explorer " + outPath);
			} catch (Exception e) {
				log.error("下载异常",e);
			}
		}*/
		return null;
	}

}
