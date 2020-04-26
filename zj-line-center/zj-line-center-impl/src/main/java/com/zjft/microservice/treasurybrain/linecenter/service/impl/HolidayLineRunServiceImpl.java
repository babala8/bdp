package com.zjft.microservice.treasurybrain.linecenter.service.impl;

import com.zjft.microservice.treasurybrain.channelcenter.web_inner.SysOrgInnerResource;
import com.zjft.microservice.treasurybrain.linecenter.dto.HolidayLineRunDTO;
import com.zjft.microservice.treasurybrain.linecenter.dto.LineInfoDTO;
import com.zjft.microservice.treasurybrain.common.dto.DTO;
import com.zjft.microservice.treasurybrain.common.util.RetCodeEnum;
import com.zjft.microservice.treasurybrain.common.util.StringUtil;
import com.zjft.microservice.treasurybrain.linecenter.repository.HolidayLineRunMapper;
import com.zjft.microservice.treasurybrain.linecenter.service.HolidayLineRunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class HolidayLineRunServiceImpl implements HolidayLineRunService {
	
	@Resource
	private HolidayLineRunMapper holidayLineRunMapper;

	@Resource
	private SysOrgInnerResource sysOrgInnerResource;

	/**
	 * 节假日线路运行图规划
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, timeout = 36000, rollbackFor = Exception.class)
	public DTO manageHolidayLineRunMap(HolidayLineRunDTO holidayLineRunDTO) throws ParseException {
		DTO dto = new DTO(RetCodeEnum.FAIL);
		String startDate = holidayLineRunDTO.getStartDate();
		String endDate = holidayLineRunDTO.getEndDate();
		List<LineInfoDTO> lineList = holidayLineRunDTO.getLineInfoDTOList();

		// 1.sub the different year-months in startDate and endDate
		List<String> yearMonthList = getYearMonth (startDate, endDate);

		// 2.loop the lineList
		for (int lineNum = 0; lineNum < lineList.size(); lineNum++) {
			// 2.1.loop the year-months
			LineInfoDTO lineInfo = lineList.get(lineNum);
			String lineNo =lineInfo.getLineNo();
			for (String yearMonth : yearMonthList) {
				// 2.1.1.check the routeRunMap exists for this line and this year-month
				int existsFlag = holidayLineRunMapper.selectExistsFlag(yearMonth,lineNo);
				if (0 == existsFlag) {
					dto.setRetCode(RetCodeEnum.FAIL.getCode());
					dto.setRetMsg("线路" + lineNo + "在" + yearMonth +"的线路运行图尚未生成,请先生成");
					return dto;
				}
			}

            // 2.2.delete the old detail of this routeRunMap between startDate and endDate,add new details
			// delete

			String startLineRunNo = lineNo + startDate.replace("-","");
			String endLineRunNo = lineNo + endDate.replace("-","");
			holidayLineRunMapper.deleteLine(startLineRunNo,endLineRunNo);

			// add new detail to the line in the day
			List<String> addDateList = lineInfo.getAddDateList();

			ArrayList<HolidayLineRunDTO> holidayLineRunDTOArrayList = new ArrayList<HolidayLineRunDTO>();
			if (null != addDateList) {
				for (int i = 0; i < addDateList.size(); i++) {
					String date = StringUtil.parseString(addDateList.get(i));
					String lineRunNo = lineNo + date.replace("-","");
					Integer orgGradeNo = 5;
					//根据线路号查询所有网点
                  List<String> orgNoList = sysOrgInnerResource.selectOrgNoList(lineNo,orgGradeNo);
                  for(int j = 0;j<orgNoList.size();j++){
					  HolidayLineRunDTO holidayLineRunDTO1 = new HolidayLineRunDTO();
					  String orgNo = orgNoList.get(j);
					  holidayLineRunDTO1.setLineWorkId(lineRunNo);
					  holidayLineRunDTO1.setCustomerNo(orgNo);
					  holidayLineRunDTO1.setSortNo(j);
					  holidayLineRunDTO1.setCustomerType(3);
					  holidayLineRunDTOArrayList.add(holidayLineRunDTO1);
//					  holidayLineRunMapper.insertLineRunNetDeatil(lineRunNo,orgNo,j);
				  }

				}
			}
			holidayLineRunMapper.insertLineRunNetDeatil(holidayLineRunDTOArrayList);
            // 2.3.update the ROUTE_RUN_INFO table
			holidayLineRunMapper.updateNetCount(startLineRunNo,endLineRunNo);
		}
		dto.setRetCode(RetCodeEnum.SUCCEED.getCode());
		dto.setRetMsg(RetCodeEnum.SUCCEED.getTip());
		return dto;
	}

	private List<String> getYearMonth(String startDateStr, String endDateStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = dateFormat.parse(startDateStr);
		Date endDate = dateFormat.parse(endDateStr);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		int endYear = calendar.get(Calendar.YEAR);
		int endMonth = calendar.get(Calendar.MONTH);

		calendar.setTime(startDate);
		int startYear = calendar.get(Calendar.YEAR);
		int startMonth = calendar.get(Calendar.MONTH);

		List<String> yearMonthList = new ArrayList<>();
		while (startYear <= endYear) {
			yearMonthList.add(startYear + "-" + ((startMonth + 1) >= 10 ? (startMonth + 1) : "0" + (startMonth + 1)));
			if (startYear == endYear && startMonth == endMonth) {
				break;
			}
			if (startMonth < 11) {
				startMonth++;
			} else {
				startYear++;
				startMonth = 0;
			}
		}
		return yearMonthList;
	}

}
